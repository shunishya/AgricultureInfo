package com.krishighar;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.InfoResponse;
import com.krishighar.db.AgricultureItemDbHelper;
import com.krishighar.db.InfoDbHelper;
import com.krishighar.gcm.AppUtil;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.JsonUtil;

public class GetInfoService extends Service implements Listener<JSONObject>,
		ErrorListener {

	private InfoDbHelper mInfoHelper;
	private AgricultureInfoPreference mPrefs;
	private AgricultureItemDbHelper mItemDbHelper;
	private int i = 0;
	private int tagCount;
	private List<String> tags;

	@Override
	public void onCreate() {
		super.onCreate();

		mInfoHelper = new InfoDbHelper(getApplicationContext());
		mItemDbHelper = new AgricultureItemDbHelper(getApplicationContext());
		tags = new ArrayList<String>();
		tags = mItemDbHelper.getTags();
		tagCount = tags.size();
		mPrefs = new AgricultureInfoPreference(getApplicationContext());
		long timestamp = mInfoHelper.getLatestTimestampOfItem(tags.get(i));
		getCropInfo(tags.get(i), timestamp);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void getCropInfo(String tag, long timestamp) {
		if (timestamp != 0) {
			if (mPrefs.shouldPullInfo(tag)) {
				String url = KrishiGharUrls.GET_CROP_INFO_URL + tag + "/n/"
						+ timestamp;
				JsonObjectRequest jsonRequest = new JsonObjectRequest(
						Method.GET, url, null, this, this);
				AppUtil.getInstance().addToRequestQueue(jsonRequest);
			} else {
				i++;
				if (i < tagCount) {
					getCropInfo(tags.get(i),
							mInfoHelper.getLatestTimestampOfItem(tags.get(i)));
				}

			}
		} else {
			String url = KrishiGharUrls.GET_CROP_INFO_URL + tag;
			JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET,
					url, null, this, this);
			AppUtil.getInstance().addToRequestQueue(jsonRequest);
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(getApplicationContext(),
				"Connection receiver:" + error.toString(), Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onResponse(JSONObject res) {
		InfoResponse response = (InfoResponse) JsonUtil.readJsonString(
				res.toString(), InfoResponse.class);
		if (response.getInfos() != null) {
			mInfoHelper.addInfo(response.getInfos(), tags.get(i));
		}
		i++;
		if (i < tagCount) {
			getCropInfo(tags.get(i),
					mInfoHelper.getLatestTimestampOfItem(tags.get(i)));
		} else {
			stopSelf();
		}
	}

}
