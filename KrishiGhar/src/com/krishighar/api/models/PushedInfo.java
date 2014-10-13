package com.krishighar.api.models;

import java.util.ArrayList;

public class PushedInfo {
	private String infoTitle;
	private String infoBody;
	private ArrayList<String> tags;
	private long timestamp;
	private String infoFrom;

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoBody() {
		return infoBody;
	}

	public void setInfoBody(String infoBody) {
		this.infoBody = infoBody;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getInfoFrom() {
		return infoFrom;
	}

	public void setInfoFrom(String infoFrom) {
		this.infoFrom = infoFrom;
	}
}
