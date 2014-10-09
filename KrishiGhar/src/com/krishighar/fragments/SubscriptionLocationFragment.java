package com.krishighar.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.krishighar.activities.MainActivity;

public class SubscriptionLocationFragment extends SherlockListFragment {
	private MainActivity mActivity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, LOCATION));
		mActivity.getSupportActionBar().setTitle("Select Location");
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
		mActivity.onLocationSelected(LOCATION[position], position);
	}
}
