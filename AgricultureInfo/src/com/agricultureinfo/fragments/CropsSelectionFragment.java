package com.agricultureinfo.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.agriculture.agricultureinfo.R;
import com.agricultureinfo.activities.MainActivity;

public class CropsSelectionFragment extends SherlockFragment {
	private MainActivity mActivity;
	private ListView mLvCrops;
	private ArrayList<String> mCrops;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> selectedCrops;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_crop_selection,
				container, false);
		mLvCrops = (ListView) rootView.findViewById(R.id.lvCrops);
		mCrops = new ArrayList<String>();
		selectedCrops = new ArrayList<String>();
		mCrops.add("Wheat");
		mCrops.add("Potato");
		mCrops.add("Tomato");
		mCrops.add("Cauliflower");
		mAdapter = new ArrayAdapter<String>(getSherlockActivity(),
				android.R.layout.simple_list_item_multiple_choice, mCrops);
		mLvCrops.setAdapter(mAdapter);
		mLvCrops.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				if (selectedCrops.contains((String) parent
						.getItemAtPosition(position))) {
					selectedCrops.remove((String) parent
							.getItemAtPosition(position));

				} else {
					selectedCrops.add((String) parent
							.getItemAtPosition(position));

				}
			}
		});
		this.setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.done) {
			mActivity.setmCrops(selectedCrops);
			mActivity.gotoFeed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}

}
