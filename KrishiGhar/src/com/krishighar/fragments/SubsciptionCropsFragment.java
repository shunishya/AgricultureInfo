package com.krishighar.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.krishighar.R;
import com.krishighar.adapters.AgricultureCategoryAdapter;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.AgricultureCategory;
import com.krishighar.api.models.GetAgricultureCategoryResponse;
import com.krishighar.db.models.AgricultureItem;
import com.krishighar.gcm.AppUtil;
import com.krishighar.interfaces.SubcriptionListener;
import com.krishighar.models.AgricultureCategoryInfo;
import com.krishighar.models.SelectableAgriculturalItems;
import com.krishighar.utils.AgricultureInfoPreference;
import com.krishighar.utils.JsonUtil;
import com.krishighar.utils.StringHelper;

public class SubsciptionCropsFragment extends SherlockFragment implements
		Listener<JSONObject>, ErrorListener, OnChildClickListener {

	String tag_json_obj = "json_obj_req_crop";
	private AgricultureInfoPreference mPref;
	private SubcriptionListener mSubscriptionListener;
	private ExpandableListView agricultureCategoryList;
	private AgricultureCategoryAdapter mAdapter;
	private List<AgricultureItem> selectedItems;
	private ProgressBar mProgressBar;

	public SubsciptionCropsFragment(SubcriptionListener subscriptionListener) {
		this.mSubscriptionListener = subscriptionListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.agriculture_category_item_list, container, false);
		mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
		agricultureCategoryList = (ExpandableListView) rootView
				.findViewById(R.id.agricuture_list);
		selectedItems = new ArrayList<AgricultureItem>();
		setGroupIndicatorToRight();
		agricultureCategoryList.setOnChildClickListener(this);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPref = new AgricultureInfoPreference(getSherlockActivity());

		getSherlockActivity().getSupportActionBar().setTitle(
				StringHelper.getCropdFragTitle(mPref.getLanguage()));
		mProgressBar.setVisibility(View.VISIBLE);
		agricultureCategoryList.setVisibility(View.GONE);
		requestForCrops();
	}

	private void requestForCrops() {
		String url = KrishiGharUrls.GET_CROPS_URL + mPref.getLocationId();
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, url,
				null, this, this);
		AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onStop() {
		super.onStop();
		AppUtil.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	}

	private void setGroupIndicatorToRight() {
		/* Get the screen width */
		DisplayMetrics dm = new DisplayMetrics();
		getSherlockActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		int width = dm.widthPixels;

		agricultureCategoryList.setIndicatorBounds(
				width - getDipsFromPixel(35), width - getDipsFromPixel(5));
	}

	// Convert pixel to dip
	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	public List<AgricultureItem> getSubscribedItems() {
		return selectedItems;
	}

	private boolean hasAnyItemSelected() {
		return selectedItems.size() > 0 ? true : false;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(getSherlockActivity(),
				"Get Crops error::" + error.toString(), Toast.LENGTH_SHORT)
				.show();
		mProgressBar.setVisibility(View.GONE);
		Button btnTryAgain = mSubscriptionListener.onVolleyError();
		agricultureCategoryList.setVisibility(View.GONE);
		btnTryAgain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mProgressBar.setVisibility(View.VISIBLE);
				requestForCrops();
				agricultureCategoryList.setVisibility(View.VISIBLE);
				mSubscriptionListener.onRequest();
			}
		});

	}

	@Override
	public void onResponse(JSONObject response) {
		GetAgricultureCategoryResponse res = (GetAgricultureCategoryResponse) JsonUtil
				.readJsonString(response.toString(),
						GetAgricultureCategoryResponse.class);
		mProgressBar.setVisibility(View.GONE);
		agricultureCategoryList.setVisibility(View.VISIBLE);
		mAdapter = new AgricultureCategoryAdapter(getSherlockActivity(),
				createCategoryList(res.getAgricultureCategories()),
				createCollection(res.getAgricultureCategories()));
		agricultureCategoryList.setAdapter(mAdapter);

	}

	private Map<Integer, List<SelectableAgriculturalItems>> createCollection(
			List<AgricultureCategory> categories) {
		Map<Integer, List<SelectableAgriculturalItems>> itemsList = new HashMap<Integer, List<SelectableAgriculturalItems>>();
		for (AgricultureCategory agricultureCategory : categories) {
			List<SelectableAgriculturalItems> items = new ArrayList<SelectableAgriculturalItems>();
			for (AgricultureItem item : agricultureCategory.getInfoAbout()) {
				items.add(new SelectableAgriculturalItems(item));
			}
			itemsList.put(agricultureCategory.getId(), items);
		}
		return itemsList;
	}

	private List<AgricultureCategoryInfo> createCategoryList(
			List<AgricultureCategory> categories) {
		List<AgricultureCategoryInfo> list = new ArrayList<AgricultureCategoryInfo>();
		for (AgricultureCategory agricultureCategory : categories) {
			AgricultureCategoryInfo category = new AgricultureCategoryInfo();
			category.setId(agricultureCategory.getId());
			category.setNameEn(agricultureCategory.getNameEn());
			category.setNameNp(agricultureCategory.getNameNp());
			list.add(category);
		}
		return list;

	}

	@Override
	public boolean onChildClick(ExpandableListView prentView, View view,
			int groupPosition, int childPosition, long id) {
		SelectableAgriculturalItems item = mAdapter.getChild(groupPosition,
				childPosition);

		item.setChecked(item.isChecked() ? false : true);
		item.getItems().setTag(
				String.valueOf(mSubscriptionListener.getLocationId()) + "-"
						+ String.valueOf(item.getItems().getCropId()));
		mAdapter.notifyDataSetChanged();
		if (item.isChecked()) {
			selectedItems.add(item.getItems());
			mSubscriptionListener.enableDoneMenuItem(true);
		} else {
			selectedItems.remove(item.getItems());
			mSubscriptionListener.enableDoneMenuItem(hasAnyItemSelected());
		}
		return true;
	}
}
