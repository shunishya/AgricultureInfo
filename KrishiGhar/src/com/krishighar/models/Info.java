package com.krishighar.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Info {
	private String from;
	private String titleEn;
	private String bodyEn;
	private long timestamp;
	private String bodyNp;
	private String titleNp;
	private int id;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getBodyEn() {
		return bodyEn;
	}

	public void setBodyEn(String bodyEn) {
		this.bodyEn = bodyEn;
	}

	public String getBodyNp() {
		return bodyNp;
	}

	public void setBodyNp(String bodyNp) {
		this.bodyNp = bodyNp;
	}

	public String getTitleNp() {
		return titleNp;
	}

	public void setTitleNp(String titleNp) {
		this.titleNp = titleNp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
