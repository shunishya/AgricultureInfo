package com.krishighar.fragments;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.VolleyError;
import com.krishighar.R;
import com.krishighar.adapters.ProviderInfoAdapter;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.ProvidersInfoResponse;
import com.krishighar.gcm.AppUtil;
import com.krishighar.utils.JsonUtil;

public class ProvidersInformationFragment extends SherlockFragment implements
		Listener<JSONObject>, ErrorListener {

	private String tag_json_obj = "json_obj_req_get_provider_info";
	private ListView lvInformations;
	private ProviderInfoAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_provider_information, container, false);
		lvInformations = (ListView) rootView.findViewById(R.id.lvInformation);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mAdapter != null) {
			mAdapter.checkLanguage();
			mAdapter.notifyDataSetChanged();
		} else {
			JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET,
					KrishiGharUrls.GET_PROVIDERS_INFO, null, this, this);
			AppUtil.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		AppUtil.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(getSherlockActivity(),
				"Get Providers info:" + error.toString(), Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onResponse(JSONObject res) {
		ProvidersInfoResponse response = (ProvidersInfoResponse) JsonUtil
				.readJsonString(res.toString(), ProvidersInfoResponse.class);
		mAdapter = new ProviderInfoAdapter(getSherlockActivity(),
				response.getInfos());
		lvInformations.setAdapter(mAdapter);

	}

}
