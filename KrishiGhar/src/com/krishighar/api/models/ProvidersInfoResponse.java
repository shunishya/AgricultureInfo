package com.krishighar.api.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProvidersInfoResponse extends BaseResponse {

	@JsonProperty("body")
	private ArrayList<ProviderInfo> infos;

	/**
	 * @return the infos
	 */
	public ArrayList<ProviderInfo> getInfos() {
		return infos;
	}

	/**
	 * @param infos
	 *            the infos to set
	 */
	public void setInfos(ArrayList<ProviderInfo> infos) {
		this.infos = infos;
	}

}
