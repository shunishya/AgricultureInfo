package com.krishighar.activities;

import com.google.android.gcm.GCMRegistrar;
import com.krishighar.api.KrishiGharApi;
import com.krishighar.api.KrishiGharException;
import com.krishighar.api.models.BaseResponse;
import com.krishighar.api.models.SendGCMRegistrationId;
import com.krishighar.utils.DeviceUtils;

import android.content.Context;
import android.os.AsyncTask;

public class SendGCMId {
	private Context mContext;

	public SendGCMId(Context context) {
		this.mContext = context;
	}

	public void sendId() {
		SendGCMRegistrationId request = new SendGCMRegistrationId();
		request.setDeviceId(DeviceUtils.getUniqueDeviceID(mContext));
		request.setRegId(GCMRegistrar.getRegistrationId(mContext));
		new SendingGCMRegistrationId().execute(request);
	}

	public class SendingGCMRegistrationId extends
			AsyncTask<SendGCMRegistrationId, Void, Object> {
		KrishiGharApi api = new KrishiGharApi(mContext);

		@Override
		protected Object doInBackground(SendGCMRegistrationId... params) {
			try {
				return api.sendGCMRegistrationId(params[0]);
			} catch (KrishiGharException e) {
				e.printStackTrace();
				return e;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (result instanceof BaseResponse) {

			} else if (result instanceof KrishiGharException) {

			}
		}

	}

}
