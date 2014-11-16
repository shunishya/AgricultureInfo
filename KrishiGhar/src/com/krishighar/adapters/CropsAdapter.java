package com.krishighar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.krishighar.R;
import com.krishighar.fragments.LanguageChooseFrag;
import com.krishighar.models.CropsListItem;
import com.krishighar.utils.AgricultureInfoPreference;

import java.util.List;

public class CropsAdapter extends ArrayAdapter<CropsListItem> {
	private LayoutInflater mInflater;
	private AgricultureInfoPreference mPrefs;
	private boolean isLanguageEn;

	public CropsAdapter(Context context, List<CropsListItem> objects) {
		super(context, R.id.tvCrop, objects);
		mInflater = LayoutInflater.from(context);
		mPrefs = new AgricultureInfoPreference(context);
		isLanguageEn = mPrefs.getLanguage() == LanguageChooseFrag.ENGLISH ? true
				: false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		CropsListItem item = getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item_crop, null);
			holder.tvCropName = (TextView) convertView
					.findViewById(R.id.tvCrop);
			holder.ivChecked = (ImageView) convertView
					.findViewById(R.id.ivCropCheck);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (isLanguageEn) {
			holder.tvCropName.setText(item.getCrop().getNameEn());
		} else {
			holder.tvCropName.setText(item.getCrop().getNameNp());
		}
		if (item.isChecked()) {
			holder.ivChecked.setVisibility(View.VISIBLE);
		} else {
			holder.ivChecked.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	class ViewHolder {
		public TextView tvCropName;
		public ImageView ivChecked;
	}

}
