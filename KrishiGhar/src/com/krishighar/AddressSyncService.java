package com.krishighar;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.krishighar.api.KrishiGharUrls;
import com.krishighar.api.models.SyncContactRequest;
import com.krishighar.gcm.AppUtil;
import com.krishighar.models.Contacts;
import com.krishighar.utils.DeviceUtils;
import com.krishighar.utils.JsonUtil;
import com.krishighar.utils.PhoneNumberHelper;

public class AddressSyncService extends Service implements
		Listener<JSONObject>, ErrorListener {
	private ArrayList<Contacts> contacts;
	private PhoneNumberHelper mPhoneHelper;

	@Override
	public void onCreate() {
		super.onCreate();
		contacts = new ArrayList<>();
		getPhoneBookContacts();
		mPhoneHelper = new PhoneNumberHelper();
		SyncContactRequest syncRequest = new SyncContactRequest();
		syncRequest.setContacts(contacts);
		syncRequest.setDeviceId(DeviceUtils
				.getUniqueDeviceID(getApplicationContext()));
		JSONObject object = null;
		try {
			object = new JSONObject(JsonUtil.writeValue(syncRequest));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.POST,
				KrishiGharUrls.SYNC_CONTACTS_URL, object, this, this);
		AppUtil.getInstance().addToRequestQueue(jsonRequest);

	}

	/**
	 * Get Contacts from phone book here
	 */
	public void getPhoneBookContacts() {

		Cursor phones = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			phoneNumber = phoneNumber.replaceAll("-", "");
			phoneNumber = phoneNumber.replaceAll(" ", "");
			Contacts contact = mPhoneHelper.getContactNumberCountry(
					phoneNumber, "NP");
			contact.setName(name);
			contacts.add(contact);
		}
		phones.close();

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(getApplicationContext(),
				"Contact Sync:: please try again." + error.toString(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResponse(JSONObject res) {

	}

}
