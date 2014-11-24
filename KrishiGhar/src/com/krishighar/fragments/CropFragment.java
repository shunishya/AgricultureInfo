package com.krishighar.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
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
import com.krishighar.activities.FeedActivity;
import com.krishighar.adapters.InfoAdapter;
import com.krishighar.adapters.SubscribeItemsAdapter;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.InfoResponse;
import com.krishighar.db.AgricultureItemDbHelper;
import com.krishighar.db.InfoDbHelper;
import com.krishighar.db.models.AgricultureItem;
import com.krishighar.db.models.Info;
import com.krishighar.gcm.AppUtil;
import com.krishighar.interfaces.OnBackedPressedListener;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.JsonUtil;
import com.krishighar.utils.Network;

public class CropFragment extends SherlockFragment implements
		Listener<JSONObject>, ErrorListener, OnRefreshListener<ListView>,
		OnLastItemVisibleListener, OnItemClickListener, OnBackedPressedListener {

	private String tag_json_obj = "json_obj_req_get_info";

	private PullToRefreshListView lvInfo;
	private AgricultureItem crop;
	private InfoDbHelper mInfoDbHelper;
	private InfoAdapter mAdapter;
	private SubscribeItemsAdapter mItemsAdapter;
	private ArrayList<Info> infos;
	private AgricultureInfoPreference mPrefs;
	private GridView mGridView;
	private List<AgricultureItem> items;

	private boolean isPulledNewInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_crops_description,
				container, false);
		mPrefs = new AgricultureInfoPreference(getSherlockActivity());
		mGridView = (GridView) rootView.findViewById(R.id.subscribedItems);
		lvInfo = (PullToRefreshListView) rootView.findViewById(R.id.lvInfo);
		items = new AgricultureItemDbHelper(getSherlockActivity()).getItems();
		mItemsAdapter = new SubscribeItemsAdapter(getSherlockActivity(), items);
		lvInfo.setMode(Mode.BOTH);
		lvInfo.getLoadingLayoutProxy().setRefreshingLabel("Loading...");
		infos = new ArrayList<Info>();
		mGridView.setAdapter(mItemsAdapter);
		mInfoDbHelper = new InfoDbHelper(getSherlockActivity());
		mAdapter = new InfoAdapter(getSherlockActivity(), infos);
		lvInfo.setAdapter(mAdapter);
		lvInfo.setVisibility(View.GONE);
		lvInfo.setOnRefreshListener(this);
		lvInfo.setOnLastItemVisibleListener(this);

		mGridView.setOnItemClickListener(this);

		return rootView;
	}

	private void getCropInfo(String timestamp) {
		isPulledNewInfo = true;
		String url = KrishiGharUrls.GET_CROP_INFO_URL + crop.getTag() + "/n/"
				+ timestamp;
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, url,
				null, this, this);
		AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
	}

	@Override
	public void onStop() {
		super.onStop();
		AppUtil.getInstance().getRequestQueue().cancelAll(tag_json_obj);

	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(getSherlockActivity(),
				"Get Crops info:" + error.toString(), Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onResponse(JSONObject response) {
		InfoResponse res = (InfoResponse) JsonUtil.readJsonString(
				response.toString(), InfoResponse.class);
		if (res.getInfos() != null) {
			mInfoDbHelper.addInfo(res.getInfos(), crop.getTag());
			if (isPulledNewInfo) {
				infos.clear();
				infos.addAll(mInfoDbHelper.getAllInfo(crop.getTag(), 0));
			} else {
				infos.addAll(res.getInfos());
				if (res.getInfos().isEmpty()) {
					mPrefs.setPulledAllOldInfo(crop.getTag(), true);
				}
			}
		}
		mAdapter.notifyDataSetChanged();
		lvInfo.onRefreshComplete();
	}

	@Override
	public void onResume() {
		super.onResume();

		if (infos.isEmpty() && crop != null) {
			if (mInfoDbHelper.isTableEmpty()) {
				lvInfo.setRefreshing();
				getInfoInitially();
			} else {
				infos.addAll(mInfoDbHelper.getAllInfo(crop.getTag(), 0));
				if (infos.isEmpty()) {
					lvInfo.setRefreshing();
					getInfoInitially();
				}
				mAdapter.notifyDataSetChanged();
			}
		} else {
			mItemsAdapter.checkLanguage();
			mItemsAdapter.notifyDataSetChanged();
			mAdapter.checkLanguage();
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((FeedActivity) activity).attachListener(this);
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		if (refreshView.getCurrentMode() == Mode.PULL_FROM_END) {
			lvInfo.onRefreshComplete();

		} else {
			if (Network.isConnected(getSherlockActivity())) {
				if (mAdapter.getCount() > 0) {
					getCropInfo(mAdapter.getLatestTimestamp());
				} else {
					getCropInfo("0");
				}

			} else {
				lvInfo.onRefreshComplete();
			}
		}

	}

	private void loadInfoFromDatabase() {
		ArrayList<Info> addedInfo = mInfoDbHelper.getAllInfo(crop.getTag(),
				mAdapter.getCount());
		infos.addAll(addedInfo);
		mAdapter.notifyDataSetChanged();
		lvInfo.onRefreshComplete();
		if (addedInfo.isEmpty()) {
			if (mPrefs.isPullAllOldInfo(crop.getTag())) {
				lvInfo.setMode(Mode.PULL_FROM_START);
			} else {
				getOldInfo(mAdapter.getOldestTimeStamp());
			}
		}
	}

	@Override
	public void onLastItemVisible() {
		loadInfoFromDatabase();
	}

	public void getInfoInitially() {
		isPulledNewInfo = true;
		String url = KrishiGharUrls.GET_CROP_INFO_URL + crop.getTag();
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, url,
				null, this, this);
		AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
	}

	public void getOldInfo(String timestamp) {
		isPulledNewInfo = false;
		String url = KrishiGharUrls.GET_CROP_INFO_URL + crop.getTag() + "/o/"
				+ timestamp;
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, url,
				null, this, this);
		AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		crop = mItemsAdapter.getItem(position);
		prepareList();

	}

	private void prepareList() {
		mGridView.setVisibility(View.GONE);
		infos.clear();
		if (mInfoDbHelper.isTableEmpty()) {
			lvInfo.setRefreshing();
			getInfoInitially();
		} else {
			infos.addAll(mInfoDbHelper.getAllInfo(crop.getTag(), 0));
			if (infos.isEmpty()) {
				lvInfo.setRefreshing();
				getInfoInitially();
			}
			mAdapter.notifyDataSetChanged();
		}
		lvInfo.setVisibility(View.VISIBLE);
	}

	@Override
	public void onBackPress() {
		if (mGridView.getVisibility() == View.GONE) {
			lvInfo.setVisibility(View.GONE);
			mGridView.setVisibility(View.VISIBLE);
		} else {
			getSherlockActivity().finish();
		}
	}

}
