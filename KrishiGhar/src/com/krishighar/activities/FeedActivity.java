package com.krishighar.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.krishighar.R;
import com.krishighar.adapters.CropsPagerAdapter;
import com.krishighar.db.CropDbHelper;
import com.krishighar.db.models.Crop;

import java.util.List;

public class FeedActivity extends SherlockFragmentActivity {
	private ActionBar mActionBar;
	private List<Crop> crops;
	private ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_feed);
		mActionBar = getSupportActionBar();
		crops = new CropDbHelper(this).getCrops();
		FragmentPagerAdapter mPagerAdapter = new CropsPagerAdapter(
				getSupportFragmentManager(), crops);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setOnPageChangeListener(onPageChangeListener);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(0);
		addActionBarTabs();
	}

	private ActionBar.TabListener tabListener = new ActionBar.TabListener() {
		@Override
		public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
			mPager.setCurrentItem(tab.getPosition());
			tab.setText(crops.get(tab.getPosition()).getCropName());
		}

		@Override
		public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

		}
	};

	/**
	 * call onCreate
	 * 
	 * adds the fragment titles and tab listener on the action bar
	 * */

	private void addActionBarTabs() {
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		for (Crop crop : crops) {
			ActionBar.Tab tab = mActionBar.newTab().setText(crop.getCropName())
					.setTabListener(tabListener);
			mActionBar.addTab(tab);
		}
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}

	private ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			super.onPageSelected(position);
			mActionBar.setSelectedNavigationItem(position);
			mActionBar.getTabAt(position).setText(
					crops.get(position).getCropName());
		}
	};

}
