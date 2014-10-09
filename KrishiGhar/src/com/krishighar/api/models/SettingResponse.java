package com.krishighar.api.models;

import java.util.ArrayList;

import com.krishighar.models.Tag;

public class SettingResponse {
	private ArrayList<Tag> tags;

	/**
	 * @return the tags
	 */
	public ArrayList<Tag> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

}
