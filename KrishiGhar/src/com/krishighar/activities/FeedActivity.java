package com.krishighar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.krishighar.R;
import com.krishighar.adapters.CropsPagerAdapter;
import com.krishighar.db.CropDbHelper;
import com.krishighar.db.models.Crop;
import com.krishighar.fragments.LanguageChooseFrag;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.StringHelper;

import im.dino.dbinspector.activities.DbInspectorActivity;

import java.util.List;

public class FeedActivity extends SherlockFragmentActivity {
	private ActionBar mActionBar;
	private List<Crop> crops;
	private ViewPager mPager;
	private AgricultureInfoPreference mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_feed);
		mActionBar = getSupportActionBar();

		mPrefs = new AgricultureInfoPreference(this);
		mActionBar.setTitle(StringHelper.getAppName(mPrefs.getLanguage()));

		crops = new CropDbHelper(this).getCrops();
		FragmentPagerAdapter mPagerAdapter = new CropsPagerAdapter(
				getSupportFragmentManager(), crops);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setOnPageChangeListener(onPageChangeListener);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(0);
		if (crops.size() > 1) {
			addActionBarTabs();
		} else {
			if (mPrefs.getLanguage() == LanguageChooseFrag.ENGLISH) {
				mActionBar.setTitle(crops.get(0).getNameEn());
			} else {
				mActionBar.setTitle(crops.get(0).getNameNp());
			}
		}
	}

	private ActionBar.TabListener tabListener = new ActionBar.TabListener() {
		@Override
		public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
			mPager.setCurrentItem(tab.getPosition());
			tab.setText(crops.get(tab.getPosition()).getNameEn());
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
			ActionBar.Tab tab = mActionBar.newTab().setText(crop.getNameEn())
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
			if (mPrefs.getLanguage() == LanguageChooseFrag.ENGLISH) {
				mActionBar.getTabAt(position).setText(
						crops.get(position).getNameEn());
			} else {
				mActionBar.getTabAt(position).setText(
						crops.get(position).getNameNp());
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * respective screen is shown according to the item selected from the menu
	 * 
	 * @param item
	 *            MenuItem which is selected by the user from menu.
	 * */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.showDb:
			startActivity(new Intent(this, DbInspectorActivity.class));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
