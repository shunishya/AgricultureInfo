package com.krishighar.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.krishighar.R;
import com.krishighar.activities.ChangeSubscription;

public class SettingOptionFrag extends SherlockFragment implements
		OnClickListener {
	private Button btnChangeLanguage;
	private Button btnChangeSubscription;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_settings_option,
				container, false);
		btnChangeLanguage = (Button) view.findViewById(R.id.btnChangeLanguage);
		btnChangeSubscription = (Button) view
				.findViewById(R.id.btnChangeSubscription);
		btnChangeLanguage.setOnClickListener(this);
		btnChangeSubscription.setOnClickListener(this);

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnChangeLanguage:
			getSherlockActivity().getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.container, new LanguageChooseFrag()).commit();
			break;
		case R.id.btnChangeSubscription:
			startActivity(new Intent(getSherlockActivity(),
					ChangeSubscription.class));
			getSherlockActivity().finish();
			break;

		default:
			break;
		}

	}

}
