package com.krishighar.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.krishighar.db.DbConf;

@DatabaseTable(tableName = DbConf.TABLE_INFO)
public class InfoTable {

	public static String TAG = "tag";

	@DatabaseField
	private String infoTitle;
	@DatabaseField
	private String infoBody;
	@DatabaseField
	private String tag;
	@DatabaseField
	private long timestamp;
	@DatabaseField
	private String infoFrom;

	public String getInfoFrom() {
		return infoFrom;
	}

	public void setInfoFrom(String infoFrom) {
		this.infoFrom = infoFrom;
	}

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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
