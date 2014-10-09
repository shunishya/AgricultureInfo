package com.krishighar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.krishighar.activities.MainActivity;
import com.krishighar.activities.SendGCMId;
import com.krishighar.gcm.AppUtil;
import com.krishighar.gcm.Config;
import com.krishighar.gcm.GCMMainActivity;
import com.krishighar.utils.AgricultureInfoPreference;

public class GCMIntentService extends GCMBaseIntentService {
	private AppUtil appUtil = null;

	public GCMIntentService() {
		super(Config.GOOGLE_PROJECT_ID);
	}

	@Override
	protected void onError(Context context, String errorId) {
		if (appUtil == null)
			appUtil = (AppUtil) getApplicationContext();
		appUtil.displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		if (appUtil == null)
			appUtil = (AppUtil) getApplicationContext();
		String message = intent.getExtras().getString("m");
		appUtil.displayMessage(context, message);
		generateNotification(context, message);

	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		if (appUtil == null)
			appUtil = (AppUtil) getApplicationContext();
		AgricultureInfoPreference mPrefs = new AgricultureInfoPreference(
				context);
		mPrefs.setGCMRegistrationId(registrationId);
		new SendGCMId(context).sendId();
		appUtil.displayMessage(context, "GCM Registered.");
		GCMRegistrar.setRegisteredOnServer(context, true);

	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		if (appUtil == null)
			appUtil = (AppUtil) getApplicationContext();
		appUtil.displayMessage(context, "Can't register");
		GCMRegistrar.setRegisteredOnServer(context, false);

	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		if (appUtil == null)
			appUtil = (AppUtil) getApplicationContext();
		String message = getString(R.string.gcm_deleted, total);
		appUtil.displayMessage(context, message);
		generateNotification(context, message);
	}

	private static void generateNotification(Context context, String message) {
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);
		String title = "KrishiGhar";
		Intent notificationIntent = new Intent(context, GCMMainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(0, notification);
	}

}
