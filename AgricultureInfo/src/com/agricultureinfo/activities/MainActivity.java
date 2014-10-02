package com.agricultureinfo.activities;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewConfiguration;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.agriculture.agricultureinfo.R;
import com.agricultureinfo.fragments.CropsSelectionFragment;
import com.agricultureinfo.fragments.Locationfragment;
import com.agricultureinfo.fragments.SplashFragment;
import com.agricultureinfo.utils.AgricultureInfoPreference;

public class MainActivity extends SherlockFragmentActivity {

	private AgricultureInfoPreference mPrefs;
	private String mlocation;
	private ArrayList<String> mCrops;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
			getViewConfiguration();
		}
		mPrefs = new AgricultureInfoPreference(this);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main, new SplashFragment()).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * call on onCreate
	 * 
	 * Enables the overflow menu.
	 * 
	 * */
	public void getViewConfiguration() {
		try {
			ViewConfiguration config = ViewConfiguration.get(MainActivity.this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectLocation() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main, new Locationfragment()).commit();
	}

	public void selectCrops() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main, new CropsSelectionFragment()).commit();

	}

	public boolean isUserLoggedIn() {
		return mPrefs.isUserLoggedin();
	}

	public void gotoFeed() {
		startActivity(new Intent(this, FeedActivity.class));
		finish();
	}

	/**
	 * @return the mlocation
	 */
	public String getMlocation() {
		return mlocation;
	}

	/**
	 * @param mlocation
	 *            the mlocation to set
	 */
	public void setMlocation(String mlocation) {
		this.mlocation = mlocation;
		mPrefs.setLocation(mlocation);
	}

	/**
	 * @return the mCrops
	 */
	public ArrayList<String> getmCrops() {
		return mCrops;
	}

	/**
	 * @param mCrops
	 *            the mCrops to set
	 */
	public void setmCrops(ArrayList<String> mCrops) {
		this.mCrops = mCrops;
		mPrefs.setCrops(mCrops);
	}
}
