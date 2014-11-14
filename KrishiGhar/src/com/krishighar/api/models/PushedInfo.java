package com.krishighar.api.models;

import java.util.ArrayList;

import com.krishighar.db.models.Info;

public class PushedInfo {
	private Info info;
	private ArrayList<String> tags;

	/**
	 * @return the info
	 */
	public Info getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(Info info) {
		this.info = info;
	}

	/**
	 * @return the tags
	 */
	public ArrayList<String> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

}
