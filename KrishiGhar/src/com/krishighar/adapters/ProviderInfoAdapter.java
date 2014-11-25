package com.krishighar.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.krishighar.R;
import com.krishighar.api.models.ProviderInfo;
import com.krishighar.fragments.LanguageChooseFrag;
import com.krishighar.utils.AgricultureInfoPreference;

public class ProviderInfoAdapter extends ArrayAdapter<ProviderInfo> {
	private ArrayList<ProviderInfo> providerInformation;
	private LayoutInflater mInflater;
	private AgricultureInfoPreference mPrefs;
	private boolean isLanguageEn;

	public ProviderInfoAdapter(Context context,
			ArrayList<ProviderInfo> information) {
		super(context, R.id.tvProviderName, information);
		this.mInflater = LayoutInflater.from(context);
		this.providerInformation = information;
		this.mPrefs = new AgricultureInfoPreference(context);
		this.isLanguageEn = (mPrefs.getLanguage() == LanguageChooseFrag.ENGLISH ? true
				: false);
	}

	@Override
	public int getCount() {
		return providerInformation.size();
	}

	@Override
	public ProviderInfo getItem(int position) {
		return providerInformation.get(position);
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
		ViewHolder holder = null;
		ProviderInfo info = getItem(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.provider_info_row, parent,
					false);
			holder = new ViewHolder();
			holder.tvProviderDescription = (TextView) convertView
					.findViewById(R.id.tvProviderDescription);
			holder.tvProviderName = (TextView) convertView
					.findViewById(R.id.tvProviderName);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (isLanguageEn) {
			holder.tvProviderDescription.setText(info.getDescriptionEn());
			holder.tvProviderName.setText(info.getNameEn());
		} else {
			holder.tvProviderDescription.setText(info.getDescriptionNp());
			holder.tvProviderName.setText(info.getNameNp());
		}
		return convertView;
	}

	class ViewHolder {
		public TextView tvProviderName;
		public TextView tvProviderDescription;
	}

}
