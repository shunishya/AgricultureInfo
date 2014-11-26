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

	private static final String ISCONTACTSSYNCED = "is_contact_synced";
	private static final String GCM_REGISTRATION_ID = "_gcm_registration_id";
	private static final String IS_PULLED_ALL_OLD_INFO = "is_pulled_all_info";

	public AgricultureInfoPreference(Context context) {
		mSharedPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
	}

	public boolean isContactSynced() {
		return mSharedPreferences.getBoolean(ISCONTACTSSYNCED, false);

	}

	public void setContactSynced(boolean isSynced) {
		mEditor = mSharedPreferences.edit();
		mEditor.putBoolean(ISCONTACTSSYNCED, isSynced);
		mEditor.commit();
	}

	public void setLocation(String locationName) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(LOCATION_NAME, locationName);
		mEditor.commit();
	}

	public void setLocationId(int locationId) {
		mEditor = mSharedPreferences.edit();
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

	public boolean isPullAllOldInfo(String forCrop) {
		return mSharedPreferences.getBoolean(IS_PULLED_ALL_OLD_INFO + forCrop,
				false);
	}

	public void setPulledAllOldInfo(String forCrop, boolean isPulled) {
		mEditor = mSharedPreferences.edit();
		mEditor.putBoolean(IS_PULLED_ALL_OLD_INFO + forCrop, isPulled);
		mEditor.commit();
	}

	public void setInfoPulledTime(String crop, long timestamp) {
		mEditor = mSharedPreferences.edit();
		mEditor.putLong(crop, timestamp);
		mEditor.commit();
	}

	public boolean shouldPullInfo(String crop) {
		long timestamp = mSharedPreferences.getLong(crop, 0);
		if (timestamp != 0) {
			long diff = System.currentTimeMillis() - timestamp;
			if ((diff / (1000 * 60)) >= 5) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

}
