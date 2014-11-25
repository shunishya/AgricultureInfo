package com.krishighar;

import java.lang.ref.WeakReference;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;

public class AddressSyncService extends Service {
	private LocalBinder<AddressSyncService> mBinder;

	@Override
	public void onCreate() {
		super.onCreate();
		mBinder = new LocalBinder<AddressSyncService>(this);

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

		}
		phones.close();

	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class LocalBinder<S> extends Binder {
		private WeakReference<S> mService;

		public LocalBinder(S service) {
			mService = new WeakReference<S>(service);
		}

		public S getService() {
			return mService.get();
		}

		public void close() {
			mService = null;
		}
	}

}
