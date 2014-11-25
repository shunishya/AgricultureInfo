package com.krishighar.api.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.krishighar.db.DbConf;

@DatabaseTable(tableName = DbConf.TABLE_PROVIDERS_INFO)
public class ProviderInfo {

	@DatabaseField
	private int id;
	@DatabaseField(id = true)
	private String username;
	@DatabaseField
	private String nameEn;
	@DatabaseField
	private String nameNp;
	@DatabaseField
	private String descriptionEn;
	@DatabaseField
	private String descriptionNp;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the nameEn
	 */
	public String getNameEn() {
		return nameEn;
	}

	/**
	 * @param nameEn
	 *            the nameEn to set
	 */
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	/**
	 * @return the nameNp
	 */
	public String getNameNp() {
		return nameNp;
	}

	/**
	 * @param nameNp
	 *            the nameNp to set
	 */
	public void setNameNp(String nameNp) {
		this.nameNp = nameNp;
	}

	/**
	 * @return the descriptionEn
	 */
	public String getDescriptionEn() {
		return descriptionEn;
	}

	/**
	 * @param descriptionEn
	 *            the descriptionEn to set
	 */
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	/**
	 * @return the descriptionNp
	 */
	public String getDescriptionNp() {
		return descriptionNp;
	}

	/**
	 * @param descriptionNp
	 *            the descriptionNp to set
	 */
	public void setDescriptionNp(String descriptionNp) {
		this.descriptionNp = descriptionNp;
	}

}
