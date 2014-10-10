package com.krishighar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.krishighar.R;
import com.krishighar.adapters.InfoAdapter;
import com.krishighar.models.Info;

import java.util.ArrayList;

public class CropFragment extends SherlockFragment {
	private ListView lvInfo;
	private String cropsTag;

	public static CropFragment newInstance(String tag) {
		CropFragment fragment = new CropFragment();
		fragment.cropsTag = tag;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_crops_description,
				container, false);
		lvInfo = (ListView) rootView.findViewById(R.id.lvInfo);
		Log.e("TAG", cropsTag);
		ArrayList<Info> infos = new ArrayList<Info>();
		Info info = new Info();
		info.setBody("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&apos;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		info.setTitle("Title");
		info.setFrom("Subhash");
		infos.add(info);
		lvInfo.setAdapter(new InfoAdapter(getSherlockActivity(), infos));
		return rootView;
	}

}
