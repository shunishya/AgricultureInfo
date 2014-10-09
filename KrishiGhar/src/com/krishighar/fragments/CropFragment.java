package com.krishighar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.krishighar.R;

public class CropFragment extends SherlockFragment {
	public static CropFragment newInstance() {
		CropFragment fragment = new CropFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_crops_description,
				container, false);
		return rootView;
	}

}
