package com.krishighar.api.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishighar.db.models.Crop;

public class GetCropsResponse extends BaseResponse{
	@JsonProperty("body")
	private ArrayList<Crop> crops;

	/**
	 * @return the crops
	 */
	public ArrayList<Crop> getCrops() {
		return crops;
	}

	/**
	 * @param crops the crops to set
	 */
	public void setCrops(ArrayList<Crop> crops) {
		this.crops = crops;
	}

}
