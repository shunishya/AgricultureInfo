package com.krishighar.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.krishighar.fragments.CropFragment;
import com.krishighar.fragments.ProvidersInformationFragment;

public class CropsPagerAdapter extends FragmentPagerAdapter {
	private List<String> titles;

	public CropsPagerAdapter(FragmentManager fm, List<String> titles) {
		super(fm);
		this.titles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		if (titles.get(position).equalsIgnoreCase("Providers Info")
				|| titles.get(position).equalsIgnoreCase("दाताहरूको सूचना")) {
			return new ProvidersInformationFragment();
		} else {
			return new CropFragment();
		}

	}

	@Override
	public int getCount() {
		return titles.size();
	}

}