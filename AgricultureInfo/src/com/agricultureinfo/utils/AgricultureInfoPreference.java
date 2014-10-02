package com.agricultureinfo.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class AgricultureInfoPreference {
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;

	public static final String PREFS_NAME = "AgricultureInfoPreference";
	public static final String LOCATION = "location";
	public static final String CROPSNUMBER = "";
	public static final String CROPS = "crops";
	public static final String ISLOGGEDIN = "is_loggedin";

	public AgricultureInfoPreference(Context context) {
		mSharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
	}

	public boolean isUserLoggedin() {
		return mSharedPreferences.getBoolean(ISLOGGEDIN, false);

	}

	public void isLoggedin(boolean isLoggedin) {
		mEditor = mSharedPreferences.edit();
		mEditor.putBoolean(ISLOGGEDIN, isLoggedin);
		mEditor.commit();
	}

	public void setLocation(String loc) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(LOCATION, loc);
		mEditor.commit();
	}

	public void setCrops(ArrayList<String> crops) {
		Set<String> cropSet = new HashSet<>(crops);
		mEditor = mSharedPreferences.edit();
		mEditor.putStringSet(CROPS, cropSet);
		mEditor.commit();
	}

	public ArrayList<String> getCrops() {
		Set<String> crops = mSharedPreferences.getStringSet(CROPS, null);
		ArrayList<String> selectedCrops = new ArrayList<String>();
		selectedCrops.addAll(crops);
		return selectedCrops;
	}
}
