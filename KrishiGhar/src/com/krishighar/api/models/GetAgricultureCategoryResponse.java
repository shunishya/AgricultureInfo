package com.krishighar.api.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAgricultureCategoryResponse extends BaseResponse {
	@JsonProperty("body")
	private ArrayList<AgricultureCategory> agricultureCategories = new ArrayList<AgricultureCategory>();

	/**
	 * @return the agricultureCategories
	 */
	public ArrayList<AgricultureCategory> getAgricultureCategories() {
		return agricultureCategories;
	}

	/**
	 * @param agricultureCategories
	 *            the agricultureCategories to set
	 */
	public void setAgricultureCategories(
			ArrayList<AgricultureCategory> agricultureCategories) {
		if (agricultureCategories != null) {
			this.agricultureCategories = agricultureCategories;
		}
	}

}
