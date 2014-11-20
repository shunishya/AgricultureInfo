package com.krishighar.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.krishighar.R;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.SubscribtionRequest;
import com.krishighar.db.CropDbHelper;
import com.krishighar.db.InfoDbHelper;
import com.krishighar.db.models.Crop;
import com.krishighar.fragments.SubsciptionCropsFragment;
import com.krishighar.fragments.SubscriptionLocationFragment;
import com.krishighar.gcm.AppUtil;
import com.krishighar.interfaces.SubcriptionListener;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.DeviceUtils;
import com.krishighar.utils.JsonUtil;

public class ChangeSubscription extends SherlockFragmentActivity implements
		Listener<JSONObject>, ErrorListener, SubcriptionListener {
	boolean doneVisible;
	private AgricultureInfoPreference mPrefs;
	private ArrayList<Crop> crops;

	private String tag_json_obj = "json_obj_req_update_info";
	private Button btnTryAgain;
	private View frag;
	private String locationName;
	private int locationId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscription);
		btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
		frag = findViewById(R.id.container);
		mPrefs = new AgricultureInfoPreference(ChangeSubscription.this);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.container, new SubscriptionLocationFragment(this))
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
	protected void onStop() {
		super.onStop();
		AppUtil.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	}

	@Override
	public void onBackPressed() {
		btnTryAgain.setVisibility(View.GONE);
		frag.setVisibility(View.VISIBLE);
		super.onBackPressed();
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
		} else {
			this.crops = (ArrayList<Crop>) crops;
			ArrayList<String> tags = new ArrayList<String>();
			for (Crop crop : crops) {
				tags.add(crop.getTag());
			}
			SubscribtionRequest request = new SubscribtionRequest();
			request.setDeviceId(mPrefs.getDeviceId());
			request.setPhoneNumber(DeviceUtils.getPhoneNumber(this));
			request.setTags(tags);
			JSONObject objectRequest = null;
			try {
				objectRequest = new JSONObject(JsonUtil.writeValue(request));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.POST,
					KrishiGharUrls.SUBSCRIBE_URL, objectRequest, this, this);
			AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
		}

	}

	public void onSuccessfullUpdate() {
		new AgricultureInfoPreference(this).setLocation(locationName,
				locationId);
		startActivity(new Intent(this, FeedActivity.class));
		finish();
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		Toast.makeText(ChangeSubscription.this, "Please try again later.",
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onResponse(JSONObject arg0) {
		Intent intent = new Intent();
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
		new InfoDbHelper(this).clearTable();
		CropDbHelper cropsDbHelper = new CropDbHelper(this);
		cropsDbHelper.clearTable();
		cropsDbHelper.addCrops(crops);
		onSuccessfullUpdate();
	}

	@Override
	public void onLocationSelected(String locationName, int locationId) {
		setLocationId(locationId);
		setLocationName(locationName);

		getSupportFragmentManager().beginTransaction().addToBackStack(null)
				.replace(R.id.container, new SubsciptionCropsFragment(this))
				.commit();
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName
	 *            the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the locationId
	 */
	public int getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	@Override
	public void enableDoneMenuItem(boolean enable) {
		if (doneVisible == enable)
			return;
		this.doneVisible = enable;
		supportInvalidateOptionsMenu();
	}

	@Override
	public Button onVolleyError() {
		btnTryAgain.setVisibility(View.VISIBLE);
		frag.setVisibility(View.GONE);
		return btnTryAgain;
	}

	@Override
	public void onRequest() {
		frag.setVisibility(View.VISIBLE);
		btnTryAgain.setVisibility(View.GONE);
	}
}
