package com.agricultureinfo.adapters;

import java.util.ArrayList;

import com.agricultureinfo.fragments.CropFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CropsPagerAdapeter extends FragmentPagerAdapter {
	private ArrayList<String> mTitles;

	public CropsPagerAdapeter(FragmentManager fm, ArrayList<String> titles) {
		super(fm);
		this.mTitles = titles;
	}

	@Override
	public Fragment getItem(int arg0) {
		return CropFragment.newInstance();
	}

	@Override
	public int getCount() {
		return mTitles.size();
	}

}