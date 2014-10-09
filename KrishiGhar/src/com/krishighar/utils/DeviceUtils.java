package com.krishighar.utils;

import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.util.Locale;

/**
 * Utility class for accessing various device features such as device id,
 * telephone number etc.
 * 
 * 
 */
public class DeviceUtils {

	/**
	 * Returns Android Secure ID as a unique device ID
	 * 
	 * @param context
	 * @return
	 */
	public static String getUniqueDeviceID(Context context) {
		String identifier = null;
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null)
			identifier = tm.getDeviceId();
		if (identifier == null || identifier.length() == 0)
			identifier = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
		// identifier = identifier + Math.round(100);
		return identifier;
	}
}
