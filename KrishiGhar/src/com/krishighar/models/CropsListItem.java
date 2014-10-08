package com.krishighar.models;

import com.krishighar.db.models.Crop;

public class CropsListItem {

	private Crop crop;

	private boolean isChecked;

	public CropsListItem(Crop crop) {
		setCrop(crop);
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}