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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.krishighar.R;
import com.krishighar.adapters.InfoAdapter;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.InfoResponse;
import com.krishighar.db.InfoDbHelper;
import com.krishighar.db.models.Crop;
import com.krishighar.db.models.Info;
import com.krishighar.gcm.AppUtil;
import com.krishighar.utils.JsonUtil;

public class CropFragment extends SherlockFragment implements
		Listener<JSONObject>, ErrorListener, OnRefreshListener<ListView>,
		OnLastItemVisibleListener {

	private String tag_json_obj = "json_obj_req_get_info";

	private PullToRefreshListView lvInfo;
	private Crop crop;
	private InfoDbHelper mInfoDbHelper;
	private InfoAdapter mAdapter;
	private ArrayList<Info> infos;

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
		lvInfo = (PullToRefreshListView) rootView.findViewById(R.id.lvInfo);
		lvInfo.setMode(Mode.BOTH);

		infos = new ArrayList<Info>();
		mInfoDbHelper = new InfoDbHelper(getSherlockActivity());

		mAdapter = new InfoAdapter(getSherlockActivity(), infos);
		lvInfo.setAdapter(mAdapter);
		if (mInfoDbHelper.isTableEmpty()) {
			getCropInfo();
		} else {
			infos.addAll(mInfoDbHelper.getAllInfo(crop.getTag()));
			mAdapter.notifyDataSetChanged();
		}
		lvInfo.setOnRefreshListener(this);
		lvInfo.setOnLastItemVisibleListener(this);

		return rootView;
	}

	private void getCropInfo() {
		String url = KrishiGharUrls.GET_CROP_INFO_URL + crop.getTag() + "/"
				+ System.currentTimeMillis() + "";
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET,
				url, null, this, this);
		AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
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
		infos.addAll(res.getInfos());
		mInfoDbHelper.addInfo(res.getInfos(), crop.getTag());
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();
		mAdapter.checkLanguage();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		lvInfo.onRefreshComplete();
	}

	@Override
	public void onLastItemVisible() {
		lvInfo.onRefreshComplete();
	}
}
