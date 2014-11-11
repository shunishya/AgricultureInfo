package com.krishighar.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.krishighar.db.DbConf;

@DatabaseTable(tableName = DbConf.TABLE_INFO)
public class Info {

	public static String COLUMN_ID = "id";

	@DatabaseField
	private String titleEn;
	@DatabaseField
	private String bodyEn;
	@DatabaseField
	private long timestamp;
	@DatabaseField
	private String from;
	@DatabaseField
	private String titleNp;
	@DatabaseField
	private String bodyNp;
	@DatabaseField(id = true)
	private int id;

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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTitleNp() {
		return titleNp;
	}

	public void setTitleNp(String titleNp) {
		this.titleNp = titleNp;
	}

	public String getBodyNp() {
		return bodyNp;
	}

	public void setBodyNp(String bodyNp) {
		this.bodyNp = bodyNp;
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
