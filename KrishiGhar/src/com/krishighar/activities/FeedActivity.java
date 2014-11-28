package com.krishighar.activities;

import im.dino.dbinspector.activities.DbInspectorActivity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.krishighar.AddressSyncService;
import com.krishighar.R;
import com.krishighar.adapters.CropsPagerAdapter;
import com.krishighar.fragments.LanguageChooseFrag;
import com.krishighar.interfaces.OnBackedPressedListener;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.StringHelper;

public class FeedActivity extends SherlockFragmentActivity {
	private ActionBar mActionBar;
	private ViewPager mPager;
	private AgricultureInfoPreference mPrefs;

	private boolean isLanguageEn;
	private int lang_id;
	private ArrayList<String> titles;
	private OnBackedPressedListener mListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_feed);
		mActionBar = getSupportActionBar();
		mPrefs = new AgricultureInfoPreference(this);

		lang_id = mPrefs.getLanguage();
		isLanguageEn = lang_id == LanguageChooseFrag.ENGLISH ? true : false;

		if (!mPrefs.isContactSynced()) {
			showContactSyncDialogue();
		}
		mActionBar.setTitle(StringHelper.getAppName(lang_id));
		titles = StringHelper.getTabTitles(lang_id);
		FragmentPagerAdapter mPagerAdapter = new CropsPagerAdapter(
				getSupportFragmentManager(), titles);
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
			tab.setText(titles.get(tab.getPosition()));
		}

		@Override
		public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

		}
	};

	public void attachListener(OnBackedPressedListener mListener) {
		this.mListener = mListener;
	}

	@Override
	public void onBackPressed() {
		if (mListener != null) {
			mListener.onBackPress();
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * call onCreate
	 * 
	 * adds the fragment titles and tab listener on the action bar
	 * */

	private void addActionBarTabs() {
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		for (String title : titles) {
			ActionBar.Tab tab = mActionBar.newTab().setText(title)
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
			if (isLanguageEn) {
				mActionBar.getTabAt(position).setText(titles.get(position));
			} else {
				mActionBar.getTabAt(position).setText(titles.get(position));
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
//		case R.id.showDb:
//			startActivity(new Intent(this, DbInspectorActivity.class));
//			break;
		case R.id.settings:
			startActivity(new Intent(this, SettingsActivity.class));
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean isLanguageChanged = (lang_id == mPrefs.getLanguage() ? false
				: true);
		if (isLanguageChanged) {
			lang_id = mPrefs.getLanguage();
			isLanguageEn = lang_id == LanguageChooseFrag.ENGLISH ? true : false;
			titles = StringHelper.getTabTitles(lang_id);
			for (int i = 0; i < titles.size(); i++) {
				if (isLanguageEn) {
					mActionBar.getTabAt(i).setText(titles.get(i));
				} else {
					mActionBar.getTabAt(i).setText(titles.get(i));
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void showContactSyncDialogue() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(StringHelper.getAppName(lang_id));

		alertDialog.setMessage(StringHelper
				.getContactSyncDialogMessage(lang_id));

		alertDialog.setPositiveButton(StringHelper.getPositiveValue(lang_id),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						mPrefs.setContactSynced(true);
						startService(new Intent(FeedActivity.this,
								AddressSyncService.class));
						dialog.cancel();
					}
				});

		alertDialog.setNegativeButton(StringHelper.getNegativeValue(lang_id),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						mPrefs.setContactSynced(true);
						dialog.cancel();
					}
				});

		alertDialog.show();
	}

}
