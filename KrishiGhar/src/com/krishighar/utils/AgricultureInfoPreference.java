package com.krishighar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AgricultureInfoPreference {
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;

	private static final String PREFS_NAME = "AgricultureInfoPreference";
	private static final String LOCATION_NAME = "location";
	private static final String LOCATION_ID = "location_id";

	private static final String CROPSNUMBER = "";
	private static final String CROPS = "crops";
	private static final String ISLOGGEDIN = "is_loggedin";
	private static final String GCM_REGISTRATION_ID = "_gcm_registration_id";

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

	public void putCrops() {
		mEditor = mSharedPreferences.edit();

		mEditor.commit();
	}

	public void setLocation(String locationName, int locationId) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(LOCATION_NAME, locationName);
		mEditor.putInt(LOCATION_ID, locationId);
		mEditor.commit();
	}

	public void setGCMRegistrationId(String registrationId) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(GCM_REGISTRATION_ID, registrationId);
		mEditor.commit();
	}

	public String getGCMRegistrationID() {
		return mSharedPreferences.getString(GCM_REGISTRATION_ID, null);
	}

}
