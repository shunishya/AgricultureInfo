package com.krishighar.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.krishighar.activities.MainActivity;
import com.krishighar.adapters.CropsAdapter;
import com.krishighar.api.KrishiGharApi;
import com.krishighar.api.KrishiGharException;
import com.krishighar.api.models.GetCropsRequest;
import com.krishighar.api.models.GetCropsResponse;
import com.krishighar.db.models.Crop;
import com.krishighar.models.CropsListItem;
import com.krishighar.utils.AgricultureInfoPreference;

public class SubsciptionCropsFragment extends SherlockListFragment {
	private MainActivity mActivity;
	private AgricultureInfoPreference mPref;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPref = new AgricultureInfoPreference(getSherlockActivity());

		getSherlockActivity().getSupportActionBar().setTitle("Select Crops");
		GetCropsRequest request = new GetCropsRequest();
		request.setLocationId(mPref.getLocationId());
		new GetCrops().execute(request);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
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
			mActivity.enableDoneMenuItem(true);
		} else {
			mActivity.enableDoneMenuItem(hasAnyItemSelected());
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

	public class GetCrops extends AsyncTask<GetCropsRequest, Void, Object> {
		KrishiGharApi api = new KrishiGharApi(getSherlockActivity());

		@Override
		protected Object doInBackground(GetCropsRequest... params) {
			try {
				return api.getCrops(params[0]);
			} catch (KrishiGharException e) {
				e.printStackTrace();
				return e;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (result instanceof GetCropsResponse) {
				GetCropsResponse response = (GetCropsResponse) result;
				setListAdapter(new CropsAdapter(getSherlockActivity(),
						getCropsItem(response.getCrops())));
			} else if (result instanceof KrishiGharException) {
				Toast.makeText(getSherlockActivity(),
						"Please try again later.", Toast.LENGTH_SHORT).show();
			}
		}

	}
}
