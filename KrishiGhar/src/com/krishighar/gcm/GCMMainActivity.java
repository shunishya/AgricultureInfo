package com.krishighar.gcm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.krishighar.R;

public class GCMMainActivity extends Activity {

  TextView lblGCMMessage;
  AppUtil appUtil;
  String regId;
  AsyncTask<Void, Void, Void> shareRegidTask;
  private PowerManager.WakeLock wakeLock;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gcm_main);
    appUtil = (AppUtil) getApplicationContext();
    lblGCMMessage = (TextView) findViewById(R.id.lblMessage);

    // register custom broadcast receiver
    registerReceiver(broadcastReceiver, new IntentFilter(
        Config.SHOW_MESSAGE));
    regId = getIntent().getStringExtra("regId");

    final Context context = this;
    shareRegidTask = new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        appUtil.shareRegIdWithAppServer(context, regId);
        return null;
      }

      @Override
      protected void onPostExecute(Void result) {
        shareRegidTask = null;
      }

    };
    shareRegidTask.execute(null, null, null);
  }

  // Create a broadcast receiver to get message and show on screen
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      String newGCMMessage = intent.getExtras().getString(
          Config.EXTRA_MESSAGE);
      acquireWakeLock(getApplicationContext());
      lblGCMMessage.append(newGCMMessage + "\n");
      Toast.makeText(getApplicationContext(),
          "Message: " + newGCMMessage, Toast.LENGTH_LONG)
          .show();
      if (wakeLock != null)
        wakeLock.release();
      wakeLock = null;

    }
  };

  public void acquireWakeLock(Context context) {
    if (wakeLock != null)
      wakeLock.release();

    PowerManager pm = (PowerManager) context
        .getSystemService(Context.POWER_SERVICE);

    wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
        | PowerManager.ACQUIRE_CAUSES_WAKEUP
        | PowerManager.ON_AFTER_RELEASE, "WakeLock");

    wakeLock.acquire();
  }

  @Override
  protected void onDestroy() {
    if (shareRegidTask != null) {
      shareRegidTask.cancel(true);
    }
    try {
      unregisterReceiver(broadcastReceiver);
      GCMRegistrar.onDestroy(this);
    } catch (Exception e) {
      Log.e("UnRegister ", e.getMessage());
    }
    super.onDestroy();
  }

}