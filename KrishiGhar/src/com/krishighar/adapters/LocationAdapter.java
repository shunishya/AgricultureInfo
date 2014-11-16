package com.krishighar.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.krishighar.R;
import com.krishighar.fragments.LanguageChooseFrag;
import com.krishighar.models.Location;
import com.krishighar.utils.AgricultureInfoPreference;

public class LocationAdapter extends ArrayAdapter<Location> {
	private LayoutInflater mInflater;
	private AgricultureInfoPreference mPrefs;
	private boolean isLanguageEn;

	public LocationAdapter(Context context, ArrayList<Location> locations) {
		super(context, R.id.tvLocation, locations);
		mInflater = LayoutInflater.from(context);
		mPrefs = new AgricultureInfoPreference(context);
		isLanguageEn = mPrefs.getLanguage() == LanguageChooseFrag.ENGLISH ? true
				: false;
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
		if (isLanguageEn) {
			holder.tvLocation.setText(item.getNameEn());
		} else {
			holder.tvLocation.setText(item.getNameNp());
		}

		return convertView;
	}

	class ViewHolder {
		public TextView tvLocation;

	}

}
