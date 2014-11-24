package com.krishighar.db.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.krishighar.db.DbConf;

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = DbConf.TABLE_SUBSCRIPTION_ITEMS)
public class AgricultureItem {
	public static String COLUMN_TAG = "tag";

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	@DatabaseField
	@JsonProperty("id")
	private int itemId;
	@DatabaseField
	private String nameEn;
	@DatabaseField
	private String nameNp;
	@DatabaseField
	private String tag;
	@DatabaseField
	private int type;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the cropId
	 */
	public int getCropId() {
		return itemId;
	}

	/**
	 * @param cropId
	 *            the cropId to set
	 */
	public void setCropId(int cropId) {
		this.itemId = cropId;
	}

	public String getNameNp() {
		return nameNp;
	}

	public void setNameNp(String nameNp) {
		this.nameNp = nameNp;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
