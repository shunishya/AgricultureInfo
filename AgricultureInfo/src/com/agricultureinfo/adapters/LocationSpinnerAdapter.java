package com.agricultureinfo.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class LocationSpinnerAdapter extends ArrayAdapter<String> {
	private ArrayList<String> mLocations;

	public LocationSpinnerAdapter(Context context, int resource,
			int textViewResourceId, ArrayList<String> objects) {
		super(context, resource, textViewResourceId, objects);
		this.mLocations = objects;
	}
	@Override
	public int getCount() {
		return mLocations.size();
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return super.getDropDownView(position, convertView, parent);
	}
	@Override
	public String getItem(int position) {
		return mLocations.get(position);
	}
}
