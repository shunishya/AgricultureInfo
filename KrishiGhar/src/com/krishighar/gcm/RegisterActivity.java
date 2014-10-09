package com.krishighar.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gcm.GCMRegistrar;
import com.krishighar.R;

public class RegisterActivity extends Activity {

	Button btnGCMRegister;
	Button btnAppShare;
	String regIdtest = null;
	String regId;
	AsyncTask<Void, Void, Void> mRegisterTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		final AppUtil appUtil = (AppUtil) getApplicationContext();

		// need Internet to connect with GCM
		if (!appUtil.isInternetAvailable()) {
			appUtil.showAlert(RegisterActivity.this, "Internet Not Available.",
					"Internet Connection Required.", false);
			return;
		}

		btnGCMRegister = (Button) findViewById(R.id.btnGCMRegister);
		btnGCMRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GCMRegistrar.checkDevice(RegisterActivity.this);
				GCMRegistrar.checkManifest(RegisterActivity.this);
				final String regId = GCMRegistrar
						.getRegistrationId(RegisterActivity.this);
				if (regId.equals("")) {
					GCMRegistrar.register(RegisterActivity.this,
							Config.GOOGLE_PROJECT_ID);
				} else {
					// Device is already registered on GCM, check server.
					if (GCMRegistrar
							.isRegisteredOnServer(RegisterActivity.this)) {
						// Skips registration.

					} else {
						// Try to register again, but not in the UI thread.
						// It's also necessary to cancel the thread onDestroy(),
						// hence the use of AsyncTask instead of a raw thread.
						final Context context = getApplicationContext();
						mRegisterTask = new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								boolean registered = ServerUtilities.register(
										context, regId);
								// At this point all attempts to register with
								// the app
								// server failed, so we need to unregister the
								// device
								// from GCM - the app will try to register again
								// when
								// it is restarted. Note that GCM will send an
								// unregistered callback upon completion, but
								// GCMIntentService.onUnregistered() will ignore
								// it.
								if (!registered) {
									GCMRegistrar.unregister(context);
								}
								return null;
							}

							@Override
							protected void onPostExecute(Void result) {
								mRegisterTask = null;
							}

						};
						mRegisterTask.execute(null, null, null);
					}
				}
			}
		});

		btnAppShare = (Button) findViewById(R.id.btnAppShare);
		btnAppShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						GCMMainActivity.class);
				i.putExtra("regId",
						GCMRegistrar.getRegistrationId(RegisterActivity.this));
				startActivity(i);
				finish();
			}
		});
	}

}