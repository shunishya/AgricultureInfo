package com.krishighar.gcm;

import com.google.android.gcm.GCMRegistrar;
import com.krishighar.activities.SendGCMId;

import android.content.Context;
import android.os.AsyncTask;

public class GCMRegistration {
	private Context mContext;
	private AsyncTask<Void, Void, Void> mRegisterTask;

	public GCMRegistration(Context context) {
		this.mContext = context;
	}

	public void getRegistrationId() {
		final AppUtil appUtil = (AppUtil) mContext.getApplicationContext();

		// need Internet to connect with GCM
		if (!appUtil.isInternetAvailable()) {
			appUtil.showAlert(mContext, "Internet Not Available.",
					"Internet Connection Required.", false);
			return;
		}

		GCMRegistrar.checkDevice(mContext);
		GCMRegistrar.checkManifest(mContext);
		final String regId = GCMRegistrar.getRegistrationId(mContext);
		if (regId.equals("")) {
			GCMRegistrar.register(mContext, Config.GOOGLE_PROJECT_ID);
		} else {
			// Device is already registered on GCM, check server.
			if (GCMRegistrar.isRegisteredOnServer(mContext)) {
				// Skips registration.
				new SendGCMId(mContext).sendId();
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = mContext.getApplicationContext();
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						boolean registered = ServerUtilities.register(context,
								regId);
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

}
