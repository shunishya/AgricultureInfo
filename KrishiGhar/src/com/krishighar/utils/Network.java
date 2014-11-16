package com.krishighar.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.provider.Settings;

public class Network {
	public static int MOBILE = 1;
	public static int WIFI = 2;

	public static boolean isConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState() == State.CONNECTED
				|| connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_WIFI).getState() == State.CONNECTED) {
			return true;
		} else
			return false;
	}

	public static void showSettingsAlert(final Context mContext, int lang_id) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		alertDialog.setTitle(StringHelper.getDialogTitle(lang_id));

		alertDialog.setMessage(StringHelper.getDialogMessage(lang_id));

		alertDialog.setPositiveButton(StringHelper.getPositiveValue(lang_id),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_SETTINGS);
						mContext.startActivity(intent);
						dialog.cancel();
					}
				});

		alertDialog.setNegativeButton(StringHelper.getNegativeValue(lang_id),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alertDialog.show();
	}
}