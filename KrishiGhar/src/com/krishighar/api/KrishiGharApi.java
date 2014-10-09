package com.krishighar.api;

import java.io.InputStream;

import android.content.Context;

import com.krishighar.api.models.BaseResponse;
import com.krishighar.api.models.GetCropsRequest;
import com.krishighar.api.models.GetCropsResponse;
import com.krishighar.api.models.GetInfoRequest;
import com.krishighar.api.models.GetLocationResponse;
import com.krishighar.api.models.InfoResponse;
import com.krishighar.api.models.SendGCMRegistrationId;
import com.krishighar.api.models.SettingResponse;
import com.krishighar.api.models.SettingsRequest;
import com.krishighar.api.models.SubscribtionRequest;
import com.krishighar.utils.JsonUtil;

public class KrishiGharApi extends KrishiGharBaseApi {
	Context mContext;

	public KrishiGharApi(Context context) {

		this.mContext = context;
	}

	public GetLocationResponse getLocation() throws KrishiGharException {
		GetLocationResponse response = new GetLocationResponse();
		InputStream res = getData(GET_LOCATION_URL);
		if (res != null) {
			response = (GetLocationResponse) JsonUtil.readJson(res,
					GetLocationResponse.class);
			if (response.isError()) {
				throw new KrishiGharException(response.getMessage());
			}
		} else {
			throw new KrishiGharException("Failed to get response from server");
		}
		return response;
	}

	public GetCropsResponse getCrops(GetCropsRequest request)
			throws KrishiGharException {
		GetCropsResponse response = new GetCropsResponse();
		String data = JsonUtil.writeValue(request);
		InputStream res = postData(data, GET_CROPS_URL);
		if (res != null) {
			response = (GetCropsResponse) JsonUtil.readJson(res,
					GetCropsResponse.class);
			if (response.isError()) {
				throw new KrishiGharException(response.getMessage());
			}
		} else {
			throw new KrishiGharException("Failed to get response from server");
		}
		return response;
	}

	public BaseResponse subscribe(SubscribtionRequest request)
			throws KrishiGharException {
		BaseResponse response = new BaseResponse();
		String data = JsonUtil.writeValue(request);
		InputStream res = postData(data, SUBSCRIBE_URL);
		if (res != null) {
			response = (BaseResponse) JsonUtil
					.readJson(res, BaseResponse.class);
			if (response.isError()) {
				throw new KrishiGharException(response.getMessage());
			}
		} else {
			throw new KrishiGharException("Failed to get response from server");
		}
		return response;
	}

	public InfoResponse getCropsInfo(GetInfoRequest request)
			throws KrishiGharException {
		InfoResponse response = new InfoResponse();
		String data = JsonUtil.writeValue(request);
		InputStream res = postData(data, GET_CROP_INFO_URL);
		if (res != null) {
			response = (InfoResponse) JsonUtil
					.readJson(res, InfoResponse.class);
			if (response.isError()) {
				throw new KrishiGharException(response.getMessage());
			}
		} else {
			throw new KrishiGharException("Failed to get response from server");
		}
		return response;
	}

	public SettingResponse changeSetting(SettingsRequest request)
			throws KrishiGharException {
		SettingResponse response = new SettingResponse();
		String data = JsonUtil.writeValue(request);
		InputStream res = postData(data, GET_CROP_INFO_URL);
		if (res != null) {
			response = (SettingResponse) JsonUtil.readJson(res,
					SettingResponse.class);
			if (response == null) {
				throw new KrishiGharException(
						"Failed to get response from server");
			}
		} else {
			throw new KrishiGharException("Failed to get response from server");
		}
		return response;
	}

	public BaseResponse sendGCMRegistrationId(SendGCMRegistrationId request)
			throws KrishiGharException {
		BaseResponse response = new BaseResponse();
		String data = JsonUtil.writeValue(request);
		InputStream res = postData(data, SEND_GCM_REGISTRATION_ID);
		if (res != null) {
			response = (BaseResponse) JsonUtil
					.readJson(res, BaseResponse.class);
			if (response.isError()) {
				throw new KrishiGharException(response.getMessage());
			}
		} else {
			throw new KrishiGharException("Failed to get response from server");
		}
		return response;
	}
}
