package com.krishighar.interfaces;

import android.widget.Button;

public interface SubcriptionListener {
	public void onLocationSelected(String locationName, int locationId);

	public void enableDoneMenuItem(boolean isEnable);

	public Button onVolleyError();

	public void onRequest();

	public int getLocationId();

}
