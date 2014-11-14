package com.krishighar.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.krishigar.justifiedtextview.utils.TextViewEx;
import com.krishighar.R;
import com.krishighar.db.models.Info;
import com.krishighar.fragments.LanguageChooseFrag;
import com.krishighar.utils.AgricultureInfoPreference;

public class InfoAdapter extends ArrayAdapter<Info> {
	private LayoutInflater mInflater;
	private ArrayList<Info> mInfos;
	private AgricultureInfoPreference mPrefs;

	public InfoAdapter(Context context, ArrayList<Info> infos) {
		super(context, R.id.tvTitle, infos);
		mInflater = LayoutInflater.from(context);
		this.mInfos = infos;
		this.mPrefs = new AgricultureInfoPreference(context);

	}

	@Override
	public int getCount() {
		return mInfos.size();
	}

	@Override
	public Info getItem(int position) {
		return mInfos.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Info item = getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.info_row, parent, false);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvInfo = (TextViewEx) convertView.findViewById(R.id.tvInfo);
			holder.tvFrom = (TextView) convertView.findViewById(R.id.tvFrom);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvTitle.setText(mPrefs.getLanguage()==LanguageChooseFrag.ENGLISH?item.getTitleEn():item.getTitleNp());
		holder.tvInfo.setText(mPrefs.getLanguage()==LanguageChooseFrag.ENGLISH?item.getBodyEn():item.getBodyNp(), true);
		holder.tvFrom.setText("From: " + item.getInfoFrom());

		return convertView;
	}

	class ViewHolder {
		public TextView tvTitle;
		public TextViewEx tvInfo;
		public TextView tvFrom;

	}

}
