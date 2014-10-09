package com.krishighar.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.krishighar.R;
import com.krishighar.models.Location;

public class LocationAdapter extends ArrayAdapter<Location> {
	private LayoutInflater mInflater;

	public LocationAdapter(Context context, ArrayList<Location> locations) {
		super(context, R.id.tvLocation, locations);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Location item = getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item_location,
					parent, false);
			holder.tvLocation = (TextView) convertView
					.findViewById(R.id.tvLocation);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvLocation.setText(item.getName());

		return convertView;
	}

	class ViewHolder {
		public TextView tvLocation;

	}

}
