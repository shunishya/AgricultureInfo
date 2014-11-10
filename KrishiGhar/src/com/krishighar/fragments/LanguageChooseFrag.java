package com.krishighar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.krishighar.R;
import com.krishighar.utils.AgricultureInfoPreference;

public class LanguageChooseFrag extends SherlockFragment implements
		OnClickListener {

	public static int ENGLISH = 1;
	public static int NEPALI = 2;

	private Button btnEnglish;
	private Button btnNepali;
	private AgricultureInfoPreference mPrefs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getSherlockActivity().getSupportActionBar().setTitle(
				"Select language/भाषा रोज्नुहोस");
		View view = inflater
				.inflate(R.layout.choose_language, container, false);
		btnEnglish = (Button) view.findViewById(R.id.btnEnglish);
		btnNepali = (Button) view.findViewById(R.id.btnNepali);
		mPrefs = new AgricultureInfoPreference(getSherlockActivity());
		btnEnglish.setOnClickListener(this);
		btnNepali.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnEnglish:
			mPrefs.setLanguage(ENGLISH);
			getSherlockActivity()
					.getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.container, new SubscriptionLocationFragment())
					.commit();
			break;
		case R.id.btnNepali:
			mPrefs.setLanguage(NEPALI);
			getSherlockActivity()
					.getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.container, new SubscriptionLocationFragment())
					.commit();
			break;

		default:
			break;
		}

	}
}
