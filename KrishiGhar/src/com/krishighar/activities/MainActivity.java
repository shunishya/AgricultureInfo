package com.krishighar.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gcm.GCMRegistrar;
import com.krishighar.R;
import com.krishighar.api.KrishiGharApi;
import com.krishighar.api.KrishiGharException;
import com.krishighar.api.models.BaseResponse;
import com.krishighar.api.models.SendGCMRegistrationId;
import com.krishighar.api.models.SubscribtionRequest;
import com.krishighar.db.CropDbHelper;
import com.krishighar.db.models.Crop;
import com.krishighar.fragments.SubsciptionCropsFragment;
import com.krishighar.fragments.SubscriptionLocationFragment;
import com.krishighar.gcm.GCMRegistration;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.DeviceUtils;

public class MainActivity extends SherlockFragmentActivity {
	boolean doneVisible;
	private AgricultureInfoPreference mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPrefs = new AgricultureInfoPreference(MainActivity.this);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new SubscriptionLocationFragment())
				.commit();
	}

	public void onLocationSelected(String locationName, int locationId) {
		new AgricultureInfoPreference(this).setLocation(locationName,
				locationId);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new SubsciptionCropsFragment())
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.subscription_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem done = (MenuItem) menu.findItem(R.id.done_item);
		if (doneVisible) {
			done.setVisible(true);
		} else {
			done.setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.done_item) {
			SubsciptionCropsFragment frag = (SubsciptionCropsFragment) getSupportFragmentManager()
					.findFragmentById(R.id.container);
			saveSubscribedItem(frag.getSubscribedItems());

		}
		return true;
	}

	public void saveSubscribedItem(List<Crop> crops) {
		if (crops == null) {
			Toast.makeText(this, "No crops Selected", Toast.LENGTH_LONG).show();
		} else if (crops.isEmpty()) {
			Toast.makeText(this, "No crops Selected", Toast.LENGTH_LONG).show();
		}
		ArrayList<String> tags = new ArrayList<String>();
		for (Crop crop : crops) {
			tags.add(crop.getTag());
		}
		SubscribtionRequest request = new SubscribtionRequest();
		request.setDeviceId(DeviceUtils.getUniqueDeviceID(this));
		request.setLocationId(mPrefs.getLocationId());
		request.setTags(tags);
		new Subscription().execute(request);
		new CropDbHelper(this).addCrops(crops);
	}

	public void onSuccessfullSubcription() {
		startActivity(new Intent(this, FeedActivity.class));
		finish();
	}

	public void enableDoneMenuItem(boolean enable) {
		if (doneVisible == enable)
			return;
		this.doneVisible = enable;
		supportInvalidateOptionsMenu();
	}

	public class Subscription extends
			AsyncTask<SubscribtionRequest, Void, Object> {
		KrishiGharApi api = new KrishiGharApi(MainActivity.this);

		@Override
		protected Object doInBackground(SubscribtionRequest... params) {
			try {
				return api.subscribe(params[0]);
			} catch (KrishiGharException e) {
				e.printStackTrace();
				return e;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			if (result instanceof BaseResponse) {
				BaseResponse response = (BaseResponse) result;
				GCMRegistration registration = new GCMRegistration(
						MainActivity.this);
				registration.getRegistrationId();
				onSuccessfullSubcription();
			} else if (result instanceof KrishiGharException) {
				Toast.makeText(MainActivity.this, "Please try again later.",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}
