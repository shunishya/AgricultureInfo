package com.krishighar.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.krishighar.db.DbConf;

@DatabaseTable(tableName = DbConf.TABLE_SUBSCRIPTION_CROPS)
public class Crop {
	@DatabaseField
	private int cropId;
	@DatabaseField
	private String cropName;
	@DatabaseField
	private String tag;

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
		return cropId;
	}

	/**
	 * @param cropId
	 *            the cropId to set
	 */
	public void setCropId(int cropId) {
		this.cropId = cropId;
	}

	/**
	 * @return the cropName
	 */
	public String getCropName() {
		return cropName;
	}

	/**
	 * @param cropName
	 *            the cropName to set
	 */
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

}
