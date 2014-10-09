package com.krishighar.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.krishighar.activities.MainActivity;
import com.krishighar.adapters.CropsAdapter;
import com.krishighar.db.models.Crop;
import com.krishighar.models.CropsListItem;

public class SubsciptionCropsFragment extends SherlockListFragment {
	private MainActivity mActivity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new CropsAdapter(getSherlockActivity(), getCropsItem()));
		getSherlockActivity().getSupportActionBar().setTitle("Select Crops");
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
	}

	public static final String[] CROPS = { "Maize", "Rice", "Barley", "Beans",
			"Mustard", "Sunflower", "Sunflower", "Chyuri", "Jute" };

	public List<CropsListItem> getCropsItem() {
		List<CropsListItem> cropsItem = new ArrayList<>();
		for (int i = 0; i < CROPS.length; i++) {
			Crop crop = new Crop();
			crop.setName(CROPS[i]);
			cropsItem.add(new CropsListItem(crop));
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
}
