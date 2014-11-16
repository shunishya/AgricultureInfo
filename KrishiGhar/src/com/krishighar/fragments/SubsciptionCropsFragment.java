package com.krishighar.fragments;

import java.util.ArrayList;
import java.util.List;

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
import com.krishighar.adapters.CropsAdapter;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.GetCropsResponse;
import com.krishighar.db.models.Crop;
import com.krishighar.gcm.AppUtil;
import com.krishighar.interfaces.SubcriptionListener;
import com.krishighar.models.CropsListItem;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.JsonUtil;
import com.krishighar.utils.StringHelper;

public class SubsciptionCropsFragment extends SherlockListFragment implements
		Listener<JSONObject>, ErrorListener {

	String tag_json_obj = "json_obj_req_crop";
	private AgricultureInfoPreference mPref;
	private SubcriptionListener mSubscriptionListener;

	public SubsciptionCropsFragment(SubcriptionListener subscriptionListener) {
		this.mSubscriptionListener = subscriptionListener;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPref = new AgricultureInfoPreference(getSherlockActivity());

		getSherlockActivity().getSupportActionBar().setTitle(
				StringHelper.getCropdFragTitle(mPref.getLanguage()));
		requestForCrops();
	}

	private void requestForCrops() {
		String url = KrishiGharUrls.GET_CROPS_URL + mPref.getLocationId();
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, url,
				null, this, this);
		AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onStop() {
		super.onStop();
		AppUtil.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	}

	public static final String[] CROPS = { "Maize", "Rice", "Barley", "Beans",
			"Mustard", "Sunflower", "Sunflower", "Chyuri", "Jute" };

	public List<CropsListItem> getCropsItem(ArrayList<Crop> crops) {
		List<CropsListItem> cropsItem = new ArrayList<>();
		for (int i = 0; i < crops.size(); i++) {
			cropsItem.add(new CropsListItem(crops.get(i)));
		}
		return cropsItem;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		CropsAdapter adapter = (CropsAdapter) getListAdapter();
		CropsListItem item = adapter.getItem(position);
		item.setChecked(item.isChecked() ? false : true);
		adapter.notifyDataSetChanged();
		if (item.isChecked()) {
			mSubscriptionListener.enableDoneMenuItem(true);
		} else {
			mSubscriptionListener.enableDoneMenuItem(hasAnyItemSelected());
		}
	}

	public List<Crop> getSubscribedItems() {
		List<Crop> crops = new ArrayList<>();
		CropsAdapter adapter = (CropsAdapter) getListAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).isChecked()) {
				crops.add(adapter.getItem(i).getCrop());
			}
		}
		return crops;
	}

	private boolean hasAnyItemSelected() {
		CropsAdapter adapter = (CropsAdapter) getListAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).isChecked()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(getSherlockActivity(),
				"Get Crops error::" + error.toString(), Toast.LENGTH_SHORT)
				.show();
		Button btnTryAgain = mSubscriptionListener.onVolleyError();
		btnTryAgain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				requestForCrops();
				mSubscriptionListener.onRequest();
			}
		});

	}

	@Override
	public void onResponse(JSONObject response) {
		GetCropsResponse res = (GetCropsResponse) JsonUtil.readJsonString(
				response.toString(), GetCropsResponse.class);
		setListAdapter(new CropsAdapter(getSherlockActivity(),
				getCropsItem(res.getCrops())));
	}
}
