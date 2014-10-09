package com.krishighar.api.models;

import java.util.ArrayList;

public class SettingsRequest {
	private String deviceId;
	private int locationId;
	private ArrayList<Integer> cropsIds;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public ArrayList<Integer> getCropsIds() {
		return cropsIds;
	}

	public void setCropsIds(ArrayList<Integer> cropsIds) {
		this.cropsIds = cropsIds;
	}

}
