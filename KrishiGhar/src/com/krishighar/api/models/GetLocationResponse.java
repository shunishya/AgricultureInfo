package com.krishighar.api.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishighar.models.Location;

public class GetLocationResponse extends BaseResponse {
	@JsonProperty("body")
	private ArrayList<Location> locations;

	/**
	 * @return the locations
	 */
	public ArrayList<Location> getLocations() {
		return locations;
	}

	/**
	 * @param locations
	 *            the locations to set
	 */
	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

}
