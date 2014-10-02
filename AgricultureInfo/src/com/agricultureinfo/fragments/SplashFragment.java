package com.agricultureinfo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.agriculture.agricultureinfo.R;
import com.agricultureinfo.activities.MainActivity;

public class SplashFragment extends SherlockFragment {
	private MainActivity mActivity;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_splash, container,
				false);
		getSherlockActivity().getSupportActionBar().hide();
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mActivity.isUserLoggedIn()) {
			mActivity.gotoFeed();

		} else {
			mActivity.selectLocation();
		}
	}

}
