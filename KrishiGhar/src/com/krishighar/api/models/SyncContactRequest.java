package com.krishighar.api.models;

import java.util.ArrayList;

import com.krishighar.models.Contacts;

public class SyncContactRequest {
	private String deviceId;
	private ArrayList<Contacts> contacts;

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the contacts
	 */
	public ArrayList<Contacts> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts
	 *            the contacts to set
	 */
	public void setContacts(ArrayList<Contacts> contacts) {
		this.contacts = contacts;
	}

}
