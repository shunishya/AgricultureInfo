package com.krishighar.api.models;

import java.util.ArrayList;

import com.krishighar.db.models.AgricultureItem;

public class AgricultureCategory {
	private int id;
	private String nameEn;
	private String nameNp;
	private ArrayList<AgricultureItem> infoAbout;

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
	 * @return the infoAbout
	 */
	public ArrayList<AgricultureItem> getInfoAbout() {
		return infoAbout;
	}

	/**
	 * @param infoAbout
	 *            the infoAbout to set
	 */
	public void setInfoAbout(ArrayList<AgricultureItem> infoAbout) {
		this.infoAbout = infoAbout;
	}

}
