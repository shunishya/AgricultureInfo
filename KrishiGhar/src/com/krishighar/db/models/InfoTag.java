package com.krishighar.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.krishighar.db.DbConf;

@DatabaseTable(tableName = DbConf.TABLE_INFO_TAG)
public class InfoTag {

	public static String COLUMN_TAG = "tag";

	@DatabaseField(id = true)
	private String id;
	@DatabaseField
	private int info_id;
	@DatabaseField
	private String tag;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public int getInfo_id() {
		return info_id;
	}

	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
