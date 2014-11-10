package com.krishighar.fragments;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.krishighar.R;
import com.krishighar.adapters.InfoAdapter;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.InfoResponse;
import com.krishighar.db.InfoDbHelper;
import com.krishighar.db.models.Crop;
import com.krishighar.gcm.AppUtil;
import com.krishighar.models.Info;
import com.krishighar.utils.JsonUtil;

public class CropFragment extends SherlockFragment implements
		Listener<JSONObject>, ErrorListener {

	private String tag_json_obj = "json_obj_req_get_info";

	private ListView lvInfo;
	private Crop crop;
	private InfoDbHelper mInfoDbHelper;

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
		String url = KrishiGharUrls.GET_CROP_INFO_URL + crop.getTag() + "/"
				+ System.currentTimeMillis() + "";
		if (mInfoDbHelper.isTableEmpty()) {
			JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET,
					url, null, this, this);
			AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
		} else {
			lvInfo.setAdapter(new InfoAdapter(getSherlockActivity(),
					mInfoDbHelper.getAllInfo(crop.getTag())));
		}
		return rootView;
	}

	@Override
	public void onStop() {
		super.onStop();
		AppUtil.getInstance().getRequestQueue().cancelAll(tag_json_obj);

	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		Toast.makeText(getSherlockActivity(), "Please try again.",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResponse(JSONObject response) {
		InfoResponse res = (InfoResponse) JsonUtil.readJsonString(
				response.toString(), InfoResponse.class);
		lvInfo.setAdapter(new InfoAdapter(getSherlockActivity(), res.getInfos()));
		mInfoDbHelper.addInfo(res.getInfos(), crop.getTag());
	}
}
