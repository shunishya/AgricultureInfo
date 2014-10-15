package com.krishighar.fragments;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
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
import com.krishighar.db.InfoDbHelper;
import com.krishighar.db.models.Crop;
import com.krishighar.models.Info;

public class CropFragment extends SherlockFragment {
	private ListView lvInfo;
	private Crop crop;
	private InfoDbHelper mInfoDbHelper;
	private AsyncTask<GetInfoRequest, Void, Object> getCropsInfo;

	public static CropFragment newInstance(Crop id) {
		CropFragment fragment = new CropFragment();
		fragment.crop = id;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_crops_description,
				container, false);
		lvInfo = (ListView) rootView.findViewById(R.id.lvInfo);
		mInfoDbHelper = new InfoDbHelper(getSherlockActivity());
		ArrayList<Info> infos = new ArrayList<Info>();
		lvInfo.setAdapter(new InfoAdapter(getSherlockActivity(), infos));
		lvInfo.setDivider(null);
		GetInfoRequest request = new GetInfoRequest();
		request.setCropId(crop.getCropId());
		request.setTimestamp(System.currentTimeMillis());
		getCropsInfo = new GetCropInfo();
		getCropsInfo.execute(request);
		return rootView;
	}

	@Override
	public void onStop() {
		super.onStop();
		if (getCropsInfo != null && getCropsInfo.getStatus() == Status.RUNNING)
			getCropsInfo.cancel(true);
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
				//mInfoDbHelper.addInfo(response.getInfos(), crop.getTag());
			} else if (result instanceof KrishiGharException) {
				Toast.makeText(getSherlockActivity(), "Please try again.",
						Toast.LENGTH_SHORT).show();

			}
		}

	}
}
