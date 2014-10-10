package com.krishighar.adapters;

import java.util.List;

import com.krishighar.db.models.Crop;
import com.krishighar.fragments.CropFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CropsPagerAdapeter extends FragmentPagerAdapter {
	private List<Crop> mTitles;

	public CropsPagerAdapeter(FragmentManager fm, List<Crop> titles) {
		super(fm);
		this.mTitles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		return CropFragment.newInstance(mTitles.get(position).getTag());
	}

	@Override
	public int getCount() {
		return mTitles.size();
	}

}