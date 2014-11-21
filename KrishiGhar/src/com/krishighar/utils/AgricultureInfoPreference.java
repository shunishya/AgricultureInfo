package com.krishighar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AgricultureInfoPreference {
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;

	private static final String PREFS_NAME = "AgricultureInfoPreference";
	private static final String LOCATION_NAME = "location";
	private static final String LOCATION_ID = "location_id";
	private static final String PREFFERED_LANGUAGE = "preffered_lang";
	private static final String DEVICE_ID = "_deviceId";

	private static final String ISLOGGEDIN = "is_loggedin";
	private static final String GCM_REGISTRATION_ID = "_gcm_registration_id";
	private static final String IS_SUBSCRIPTION_CHANGE = "_is_subscription_change";

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

	public int getLocationId() {
		return mSharedPreferences.getInt(LOCATION_ID, 0);
	}

	public String getLocation() {
		return mSharedPreferences.getString(LOCATION_NAME, null);
	}

	public boolean isGCMRegistrationIdSame(String newId) {
		if (mSharedPreferences.getString(GCM_REGISTRATION_ID, null).equals(
				newId)) {
			return true;
		} else {
			return false;
		}
	}

	public void setGCMRegistrationId(String registrationId) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(GCM_REGISTRATION_ID, registrationId);
		mEditor.commit();
	}

	public String getGCMRegistrationID() {
		return mSharedPreferences.getString(GCM_REGISTRATION_ID, null);
	}

	public void setLanguage(int lang_id) {
		mEditor = mSharedPreferences.edit();
		mEditor.putInt(PREFFERED_LANGUAGE, lang_id);
		mEditor.commit();
	}

	public int getLanguage() {
		return mSharedPreferences.getInt(PREFFERED_LANGUAGE, -1);
	}

	public String getDeviceId() {
		return mSharedPreferences.getString(DEVICE_ID, null);
	}

	public void setDeviceId(String deviceID) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(DEVICE_ID, deviceID);
		mEditor.commit();
	}

	public boolean isSubscriptionChange() {
		return mSharedPreferences.getBoolean(IS_SUBSCRIPTION_CHANGE, false);
	}

	public void setIsSubscriptionChange(boolean isChanged) {
		mEditor = mSharedPreferences.edit();
		mEditor.putBoolean(IS_SUBSCRIPTION_CHANGE, isChanged);
		mEditor.commit();
	}

}
