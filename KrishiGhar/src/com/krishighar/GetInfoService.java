package com.krishighar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.krishighar.db.AgricultureItemDbHelper;
import com.krishighar.db.InfoDbHelper;
import com.krishighar.db.models.AgricultureItem;

import org.json.JSONObject;

import java.util.ArrayList;

public class GetInfoService extends Service implements Listener<JSONObject>,
		ErrorListener {

	private InfoDbHelper mInfoDbHelper;
	private AgricultureItemDbHelper mItemsDbHelper;
	private ArrayList<AgricultureItem> items;

	@Override
	public void onCreate() {
		super.onCreate();
		mInfoDbHelper = new InfoDbHelper(getApplicationContext());
		mItemsDbHelper = new AgricultureItemDbHelper(getApplicationContext());
		items = new ArrayList<AgricultureItem>();
		items = (ArrayList<AgricultureItem>) mItemsDbHelper.getItems();

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onResponse(JSONObject res) {
		// TODO Auto-generated method stub

	}

}
