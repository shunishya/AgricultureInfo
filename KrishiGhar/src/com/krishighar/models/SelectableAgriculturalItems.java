package com.krishighar.models;

import com.krishighar.db.models.AgricultureItem;

public class SelectableAgriculturalItems {

	private AgricultureItem items;

	private boolean isChecked;

	public SelectableAgriculturalItems(AgricultureItem items) {
		setItems(items);
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * @return the items
	 */
	public AgricultureItem getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(AgricultureItem items) {
		this.items = items;
	}

}