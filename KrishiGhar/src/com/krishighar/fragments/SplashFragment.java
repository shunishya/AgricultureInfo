package com.krishighar.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.krishighar.R;
import com.krishighar.activities.MainActivity;
import com.krishighar.db.CropDbHelper;

public class SplashFragment extends SherlockFragment {
	private MainActivity mActivity;
	private CropDbHelper mCropDbHelper;

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
		mActivity = (MainActivity) activity;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mActivity != null) {
			if (mCropDbHelper.getCrops().size() > 0) {
				mActivity.onSuccessfullSubcription();
			} else {
				getSherlockActivity()
						.getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.container,
								new SubscriptionLocationFragment()).commit();
			}

		}
	}
}
