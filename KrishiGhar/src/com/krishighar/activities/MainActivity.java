package com.krishighar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.krishighar.R;
import com.krishighar.db.CropDbHelper;
import com.krishighar.db.models.Crop;
import com.krishighar.fragments.SubsciptionCropsFragment;
import com.krishighar.fragments.SubscriptionLocationFragment;
import com.krishighar.utils.AgricultureInfoPreference;

import java.util.List;

public class MainActivity extends SherlockFragmentActivity {
	boolean doneVisible;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new SubscriptionLocationFragment())
				.commit();
	}

	public void onLocationSelected(String locationName, int locationId) {
		new AgricultureInfoPreference(this).setLocation(locationName,
				locationId);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new SubsciptionCropsFragment())
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.subscription_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem done = (MenuItem) menu.findItem(R.id.done_item);
		if (doneVisible) {
			done.setVisible(true);
		} else {
			done.setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.done_item) {
			SubsciptionCropsFragment frag = (SubsciptionCropsFragment) getSupportFragmentManager()
					.findFragmentById(R.id.container);
			saveSubscribedItem(frag.getSubscribedItems());
			startActivity(new Intent(this, FeedActivity.class));
		}
		return true;
	}

	public void saveSubscribedItem(List<Crop> crops) {
		if (crops == null) {
			Toast.makeText(this, "No crops Selected", Toast.LENGTH_LONG).show();
		} else if (crops.isEmpty()) {
			Toast.makeText(this, "No crops Selected", Toast.LENGTH_LONG).show();
		}
		new CropDbHelper(this).addCrops(crops);
	}

	public void enableDoneMenuItem(boolean enable) {
		if (doneVisible == enable)
			return;
		this.doneVisible = enable;
		supportInvalidateOptionsMenu();
	}

}
