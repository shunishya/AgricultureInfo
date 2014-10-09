package com.krishighar.gcm;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class AppUtil extends Application {

	void shareRegIdWithAppServer(final Context context, final String regId) {
		String serverUrl = Config.APP_SERVER_URL + regId;
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("regId", regId);
		try {
			post(serverUrl, paramsMap);
			GCMRegistrar.setRegisteredOnServer(context, true);
			displayMessage(context, "GCM RegId Shared with App Server");
			return;
		} catch (IOException e) {
			Log.e("AppUtil", "Error in sharing with App Server: " + e);
		}
	}

	private static void post(String endpoint, Map<String, String> params)
			throws IOException {

		URL serverUrl;
		try {
			serverUrl = new URL(endpoint);
		} catch (MalformedURLException e) {
			Log.e("AppUtil", "URL Connection Error: " + endpoint, e);
			throw new IllegalArgumentException("Invalid URL: " + endpoint);
		}

		StringBuilder postBody = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			postBody.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				postBody.append('&');
			}
		}
		String body = postBody.toString();
		byte[] bytes = body.getBytes();
		HttpURLConnection httpCon = null;
		try {
			httpCon = (HttpURLConnection) serverUrl.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setUseCaches(false);
			httpCon.setFixedLengthStreamingMode(bytes.length);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStream out = httpCon.getOutputStream();
			out.write(bytes);
			out.close();

			int status = httpCon.getResponseCode();
			if (status != 200) {
				throw new IOException("Post Failure. Status code: " + status);
			}
		} finally {
			if (httpCon != null) {
				httpCon.disconnect();
			}
		}
	}

	public boolean isInternetAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	public void displayMessage(Context context, String message) {
		Intent intent = new Intent(Config.SHOW_MESSAGE);
		intent.putExtra(Config.EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	public void showAlert(Context context, String title, String message,
			Boolean status) {
		AlertDialog alert = new AlertDialog.Builder(context).create();
		alert.setTitle(title);
		alert.setMessage(message);

		if (status != null)
			alert.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
		alert.show();
	}

}