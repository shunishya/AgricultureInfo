package com.krishighar.fragments;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.krishighar.adapters.LocationAdapter;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.GetLocationResponse;
import com.krishighar.gcm.AppUtil;
import com.krishighar.interfaces.SubcriptionListener;
import com.krishighar.models.Location;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.JsonUtil;
import com.krishighar.utils.StringHelper;

public class SubscriptionLocationFragment extends SherlockListFragment
		implements ErrorListener, Listener<JSONObject> {
	// Tag used to cancel the request
	String tag_json_obj = "json_obj_req";
	private AgricultureInfoPreference mPrefs;
	private SubcriptionListener mSubscriptionListener;

	private int languageId;

	public SubscriptionLocationFragment(SubcriptionListener subscriptionListener) {
		this.mSubscriptionListener = subscriptionListener;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mPrefs = new AgricultureInfoPreference(getSherlockActivity());
		languageId = mPrefs.getLanguage();

		getSherlockActivity().getSupportActionBar().setTitle(
				StringHelper.getLocationFragTitle(languageId));
		requestForLocation();
	}

	private void requestForLocation() {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET,
				KrishiGharUrls.GET_LOCATION_URL, null, this, this);
		AppUtil.getInstance()
				.addToRequestQueue(jsonObjectRequest, tag_json_obj);
	}

	@Override
	public void onStop() {
		super.onStop();
		AppUtil.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		LocationAdapter adapter = (LocationAdapter) getListAdapter();
		Location loc = (Location) adapter.getItem(position);
		mSubscriptionListener.onLocationSelected(loc.getNameEn(), loc.getId());
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(getSherlockActivity(),
				"Get location error::" + error.toString(), Toast.LENGTH_SHORT)
				.show();
		Button btnTryAgain = mSubscriptionListener.onVolleyError();
		btnTryAgain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				requestForLocation();
				mSubscriptionListener.onRequest();
			}
		});
	}

	@Override
	public void onResponse(JSONObject response) {
		GetLocationResponse res = (GetLocationResponse) JsonUtil
				.readJsonString(response.toString(), GetLocationResponse.class);
		setListAdapter(new LocationAdapter(getSherlockActivity(),
				res.getLocations()));
	}
}
