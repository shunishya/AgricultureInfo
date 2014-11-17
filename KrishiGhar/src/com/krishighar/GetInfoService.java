package com.krishighar;

import java.util.ArrayList;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.krishighar.api.models.InfoResponse;
import com.krishighar.db.CropDbHelper;
import com.krishighar.db.InfoDbHelper;
import com.krishighar.db.models.Crop;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GetInfoService extends Service implements Listener<InfoResponse>,
		ErrorListener {

	private InfoDbHelper mInfoDbHelper;
	private CropDbHelper mCropDbHelper;
	private ArrayList<Crop> crops;

	@Override
	public void onCreate() {
		super.onCreate();
		mInfoDbHelper = new InfoDbHelper(getApplicationContext());
		mCropDbHelper = new CropDbHelper(getApplicationContext());
		crops = new ArrayList<Crop>();
		crops = (ArrayList<Crop>) mCropDbHelper.getCrops();

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResponse(InfoResponse response) {
		// TODO Auto-generated method stub

	}

}
