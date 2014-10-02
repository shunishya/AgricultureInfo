package com.agricultureinfo.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.agriculture.agricultureinfo.R;
import com.agricultureinfo.activities.MainActivity;

public class Locationfragment extends SherlockFragment {
	public static ArrayList<String> locations;

	private MainActivity mActivity;
	private Spinner mLocationList;
	private ArrayAdapter<String> mSpinnerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_locationselect,
				container, false);
		getSherlockActivity().getSupportActionBar().show();
		mLocationList = (Spinner) rootView.findViewById(R.id.spinner_location);
		locations = new ArrayList<>();
		locations.add("kavre");
		locations.add("Banepa");
		locations.add("thimi");
		mSpinnerAdapter = new ArrayAdapter<>(getSherlockActivity(),
				android.R.layout.simple_dropdown_item_1line, locations);
		mLocationList.setAdapter(mSpinnerAdapter);
		this.setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.done) {
			mActivity.setMlocation((String) mLocationList.getSelectedItem());
			mActivity.selectCrops();
		}
		return super.onOptionsItemSelected(item);
	}

}
