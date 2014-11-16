package com.krishighar.activities;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.krishighar.R;
import com.krishighar.fragments.SettingOptionFrag;

public class Settings extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscription);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new SettingOptionFrag()).commit();
	}

}
