package com.krishighar.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.krishighar.activities.MainActivity;
import com.krishighar.adapters.LocationAdapter;
import com.krishighar.api.KrishiGharApi;
import com.krishighar.api.KrishiGharException;
import com.krishighar.api.models.GetLocationResponse;
import com.krishighar.models.Location;

public class SubscriptionLocationFragment extends SherlockListFragment {
	private MainActivity mActivity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mActivity.getSupportActionBar().setTitle("Select Location");
		new GetLocation().execute();
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

	public class GetLocation extends AsyncTask<Void, Void, Object> {
		KrishiGharApi api = new KrishiGharApi(getSherlockActivity());

		@Override
		protected Object doInBackground(Void... params) {
			try {
				return api.getLocation();
			} catch (KrishiGharException e) {
				e.printStackTrace();
				return e;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (result instanceof GetLocationResponse) {
				GetLocationResponse response = (GetLocationResponse) result;
				setListAdapter(new LocationAdapter(getSherlockActivity(),
						response.getLocations()));
			} else if (result instanceof KrishiGharException) {
				Toast.makeText(getSherlockActivity(),
						"Please try again later.", Toast.LENGTH_SHORT).show();
			}
		}

	}
}
