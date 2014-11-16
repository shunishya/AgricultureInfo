package com.krishighar.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.krishighar.R;
import com.krishighar.activities.Subscription;
import com.krishighar.db.CropDbHelper;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.Network;

public class SplashFragment extends SherlockFragment {
	private Subscription mActivity;
	private CropDbHelper mCropDbHelper;
	private int lang_id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_splash, container, false);
		mCropDbHelper = new CropDbHelper(getSherlockActivity());
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (Subscription) activity;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mActivity != null) {
			if (mCropDbHelper.getCrops().size() > 0) {
				lang_id = new AgricultureInfoPreference(mActivity)
						.getLanguage();
				if (Network.isConnected(getSherlockActivity())) {
					mActivity.onSuccessfullSubcription();
				} else {
					Network.showSettingsAlert(getSherlockActivity(), lang_id);
				}
			} else {
				getSherlockActivity().getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.container, new LanguageChooseFrag())
						.commit();
			}

		}
	}

}
