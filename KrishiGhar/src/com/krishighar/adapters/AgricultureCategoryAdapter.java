package com.krishighar.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.krishighar.R;
import com.krishighar.fragments.LanguageChooseFrag;
import com.krishighar.models.AgricultureCategoryInfo;
import com.krishighar.models.SelectableAgriculturalItems;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.StringHelper;

public class AgricultureCategoryAdapter extends BaseExpandableListAdapter {
	private LayoutInflater mInflater;
	private AgricultureInfoPreference mPrefs;
	private boolean isLanguageEn;
	private List<AgricultureCategoryInfo> agricultureCategories;
	private Map<Integer, List<SelectableAgriculturalItems>> items;
	private int lang_id;

	public AgricultureCategoryAdapter(Context context,
			List<AgricultureCategoryInfo> agricultureCategories,
			Map<Integer, List<SelectableAgriculturalItems>> objects) {
		mInflater = LayoutInflater.from(context);
		mPrefs = new AgricultureInfoPreference(context);
		this.lang_id = mPrefs.getLanguage();
		isLanguageEn = lang_id == LanguageChooseFrag.ENGLISH ? true : false;
		this.agricultureCategories = agricultureCategories;
		this.items = objects;
	}

	class ViewHolder {
		public TextView tvCropName;
		public ImageView ivChecked;
	}

	@Override
	public SelectableAgriculturalItems getChild(int groupPosition,
			int childPosition) {
		return items.get(agricultureCategories.get(groupPosition).getId()).get(
				childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		SelectableAgriculturalItems item = getChild(groupPosition,
				childPosition);
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
			holder.tvCropName.setText(item.getItems().getNameEn());
		} else {
			holder.tvCropName.setText(item.getItems().getNameNp());
		}
		if (item.isChecked()) {
			holder.ivChecked.setVisibility(View.VISIBLE);
		} else {
			holder.ivChecked.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		AgricultureCategoryInfo cat = agricultureCategories.get(groupPosition);
		// List<SelectableAgriculturalItems> item = items.get(cat.getId());
		return items.get(cat.getId()).size();
	}

	@Override
	public AgricultureCategoryInfo getGroup(int groupPosition) {
		return agricultureCategories.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return agricultureCategories.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		AgricultureCategoryInfo agricultureCategory = getGroup(groupPosition);
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.selection_header, parent,
					false);
		}
		TextView item = (TextView) convertView.findViewById(R.id.header);
		item.setTypeface(null, Typeface.BOLD);
		if (isLanguageEn) {
			item.setText(StringHelper.getListHeaderTitle(lang_id)
					+ agricultureCategory.getNameEn());
		} else {
			item.setText(agricultureCategory.getNameNp()
					+ StringHelper.getListHeaderTitle(lang_id));
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
