package com.krishighar.fragments;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.krishighar.R;
import com.krishighar.adapters.InfoAdapter;
import com.krishighar.api.KrishiGharApi;
import com.krishighar.api.KrishiGharException;
import com.krishighar.api.models.GetInfoRequest;
import com.krishighar.api.models.InfoResponse;
import com.krishighar.models.Info;

public class CropFragment extends SherlockFragment {
	private ListView lvInfo;
	private int cropsId;

	public static CropFragment newInstance(int id) {
		CropFragment fragment = new CropFragment();
		fragment.cropsId = id;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_crops_description,
				container, false);
		lvInfo = (ListView) rootView.findViewById(R.id.lvInfo);
		ArrayList<Info> infos = new ArrayList<Info>();
		Info info = new Info();
		info.setBody("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&apos;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		info.setTitle("Title");
		info.setFrom("Subhash");
		infos.add(info);
		lvInfo.setAdapter(new InfoAdapter(getSherlockActivity(), infos));
		lvInfo.setDivider(null);
		GetInfoRequest request = new GetInfoRequest();
		request.setCropId(cropsId);
		request.setTimestamp(System.currentTimeMillis());
		new GetCropInfo().execute(request);
		return rootView;
	}

	public class GetCropInfo extends AsyncTask<GetInfoRequest, Void, Object> {
		KrishiGharApi api = new KrishiGharApi(getSherlockActivity());

		@Override
		protected Object doInBackground(GetInfoRequest... params) {
			try {
				return api.getCropsInfo(params[0]);
			} catch (KrishiGharException e) {
				e.printStackTrace();
				return e;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (result instanceof InfoResponse) {
				InfoResponse response = (InfoResponse) result;
				lvInfo.setAdapter(new InfoAdapter(getSherlockActivity(),
						response.getInfos()));
			} else if (result instanceof KrishiGharException) {
				Toast.makeText(getSherlockActivity(), "Please try again.",
						Toast.LENGTH_SHORT).show();

			}
		}

	}
}
