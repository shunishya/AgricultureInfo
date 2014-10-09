package com.krishighar.api.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishighar.models.Info;

public class InfoResponse extends BaseResponse {
	@JsonProperty("body")
	private ArrayList<Info> infos;

	/**
	 * @return the infos
	 */
	public ArrayList<Info> getInfos() {
		return infos;
	}

	/**
	 * @param infos the infos to set
	 */
	public void setInfos(ArrayList<Info> infos) {
		this.infos = infos;
	}

}
