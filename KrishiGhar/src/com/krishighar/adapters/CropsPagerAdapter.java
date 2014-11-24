package com.krishighar.adapters;

import java.util.List;

import com.krishighar.db.models.AgricultureItem;
import com.krishighar.fragments.CropFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CropsPagerAdapter extends FragmentPagerAdapter {
	private List<AgricultureItem> crops;

	public CropsPagerAdapter(FragmentManager fm, List<AgricultureItem> titles) {
		super(fm);
		this.crops = titles;
	}

	@Override
	public Fragment getItem(int position) {
		return CropFragment.newInstance(crops.get(position));
	}

	@Override
	public int getCount() {
		return crops.size();
	}

}