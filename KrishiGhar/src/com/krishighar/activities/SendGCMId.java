package com.krishighar.activities;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gcm.GCMRegistrar;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.SendGCMRegistrationId;
import com.krishighar.gcm.AppUtil;
import com.krishighar.utils.DeviceUtils;
import com.krishighar.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class SendGCMId implements Listener<JSONObject>, ErrorListener {
	private Context mContext;
	private String tag_json_obj = "json_obj_req_GCM";

	public SendGCMId(Context context) {
		this.mContext = context;
	}

	public void sendId() {
		SendGCMRegistrationId request = new SendGCMRegistrationId();
		request.setDeviceId(DeviceUtils.getUniqueDeviceID(mContext));
		request.setRegId(GCMRegistrar.getRegistrationId(mContext));
		JSONObject object = null;
		try {
			object = new JSONObject(JsonUtil.writeValue(request));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.POST,
				KrishiGharUrls.SEND_GCM_REGISTRATION_ID, object, this, this);
		AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);

	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(mContext, "Error:: please try again.",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResponse(JSONObject response) {
		Toast.makeText(mContext, "Success.", Toast.LENGTH_SHORT).show();
	}

}
