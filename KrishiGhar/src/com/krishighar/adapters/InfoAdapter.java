package com.krishighar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.krishigar.justifiedtextview.utils.TextViewEx;
import com.krishighar.R;
import com.krishighar.models.Info;

import java.util.ArrayList;

public class InfoAdapter extends ArrayAdapter<Info> {
	private LayoutInflater mInflater;
	private ArrayList<Info> mInfos;

	public InfoAdapter(Context context, ArrayList<Info> infos) {
		super(context, R.id.tvTitle, infos);
		mInflater = LayoutInflater.from(context);
		this.mInfos = infos;

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
		holder.tvTitle.setText(item.getTitle());
		holder.tvInfo.setText(item.getBody(), true);
		// holder.tvInfo.setHyphenate(true, "*");
		// holder.tvInfo.setText(item.getBody());
		holder.tvFrom.setText("From: " + item.getFrom());

		return convertView;
	}

	class ViewHolder {
		public TextView tvTitle;
		public TextViewEx tvInfo;
		public TextView tvFrom;

	}

}
