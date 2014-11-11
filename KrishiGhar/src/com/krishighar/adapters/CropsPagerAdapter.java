package com.krishighar.adapters;

import java.util.List;

import com.krishighar.db.models.Crop;
import com.krishighar.fragments.CropFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CropsPagerAdapter extends FragmentPagerAdapter {
	private List<Crop> crops;

	public CropsPagerAdapter(FragmentManager fm, List<Crop> titles) {
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