package com.krishighar.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.krishighar.R;
import com.krishighar.db.models.AgricultureItem;
import com.krishighar.fragments.LanguageChooseFrag;
import com.krishighar.utils.AgricultureInfoPreference;

public class SubscribeItemsAdapter extends BaseAdapter {
	private List<AgricultureItem> items;
	private LayoutInflater mInflater;
	private AgricultureInfoPreference mPrefs;
	private boolean isLanguageEn;

	public SubscribeItemsAdapter(Context context, List<AgricultureItem> items) {
		this.items = items;
		mInflater = LayoutInflater.from(context);
		mPrefs = new AgricultureInfoPreference(context);
		isLanguageEn = (mPrefs.getLanguage() == LanguageChooseFrag.ENGLISH ? true
				: false);

	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public AgricultureItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void checkLanguage() {
		this.isLanguageEn = mPrefs.getLanguage() == LanguageChooseFrag.ENGLISH ? true
				: false;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AgricultureItem item = getItem(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_grid_buttons,
					parent, false);
			holder = new ViewHolder();
			holder.btnSubscribeItem = (TextView) convertView
					.findViewById(R.id.btnSubscribeItems);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (isLanguageEn) {
			holder.btnSubscribeItem.setText(item.getNameEn());
		} else {
			holder.btnSubscribeItem.setText(item.getNameNp());
		}

		return convertView;
	}

	class ViewHolder {
		public TextView btnSubscribeItem;
	}

}
