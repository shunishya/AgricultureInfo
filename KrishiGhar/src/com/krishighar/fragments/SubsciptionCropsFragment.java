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
import com.krishighar.activities.ChangeSubscription;
import com.krishighar.adapters.CropsAdapter;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.GetCropsResponse;
import com.krishighar.db.models.Crop;
import com.krishighar.gcm.AppUtil;
import com.krishighar.models.CropsListItem;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.JsonUtil;
import com.krishighar.utils.StringHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubsciptionCropsFragment extends SherlockListFragment implements
		Listener<JSONObject>, ErrorListener {

	String tag_json_obj = "json_obj_req_crop";
	private MainActivity mActivity;
	private AgricultureInfoPreference mPref;
	private ChangeSubscription mSettings;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPref = new AgricultureInfoPreference(getSherlockActivity());

		getSherlockActivity().getSupportActionBar().setTitle(
				StringHelper.getCropdFragTitle(mPref.getLanguage()));
		String url = KrishiGharUrls.GET_CROPS_URL + mPref.getLocationId();
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, url,
				null, this, this);
		AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MainActivity) {
			mActivity = (MainActivity) activity;
		} else {
			mSettings = (ChangeSubscription) activity;
		}
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
			if (mActivity != null) {
				mActivity.enableDoneMenuItem(true);
			} else {
				mSettings.enableDoneMenuItem(true);
			}
		} else {
			if (mActivity != null) {
				mActivity.enableDoneMenuItem(hasAnyItemSelected());
			} else {
				mSettings.enableDoneMenuItem(hasAnyItemSelected());
			}
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
		Toast.makeText(getSherlockActivity(), "Please try again later.",
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onResponse(JSONObject response) {
		GetCropsResponse res = (GetCropsResponse) JsonUtil.readJsonString(
				response.toString(), GetCropsResponse.class);
		setListAdapter(new CropsAdapter(getSherlockActivity(),
				getCropsItem(res.getCrops())));
	}
}
