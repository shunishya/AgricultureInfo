package com.krishighar.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.krishighar.activities.MainActivity;
import com.krishighar.adapters.LocationAdapter;
import com.krishighar.api.KrishiGharBaseApi;
import com.krishighar.api.models.GetLocationResponse;
import com.krishighar.gcm.AppUtil;
import com.krishighar.models.Location;
import com.krishighar.utils.JsonUtil;

import org.json.JSONObject;

public class SubscriptionLocationFragment extends SherlockListFragment
		implements ErrorListener, Listener<JSONObject> {
	// Tag used to cancel the request
	String tag_json_obj = "json_obj_req";
	private MainActivity mActivity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mActivity.getSupportActionBar().setTitle("Select Location");
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET,
				KrishiGharBaseApi.GET_LOCATION_URL, null, this, this);
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
		mActivity = (MainActivity) activity;
	}

	public static final String[] LOCATION = { "लालितप�?र", "ध�?लिखेल", "बनेपा",
			"ब�?टबल", "दाङ", "इलाम" };

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		LocationAdapter adapter = (LocationAdapter) getListAdapter();
		Location loc = (Location) adapter.getItem(position);
		mActivity.onLocationSelected(loc.getName(), loc.getId());
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(getSherlockActivity(), "Error:: please try again.",
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onResponse(JSONObject response) {
		GetLocationResponse res = (GetLocationResponse) JsonUtil
				.readJsonString(response.toString(), GetLocationResponse.class);
		setListAdapter(new LocationAdapter(getSherlockActivity(),
				res.getLocations()));
	}
}
