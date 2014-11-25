package com.krishighar.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.table.TableUtils;
import com.krishighar.api.models.ProviderInfo;
import com.krishighar.db.models.AgricultureItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderInfoDbHelper {
	private Dao<ProviderInfo, String> mProviderInfoDao;

	public ProviderInfoDbHelper(Context context) {
		DbHelper dbHelper = (DbHelper) OpenHelperManager.getHelper(context,
				DbHelper.class);
		try {
			this.mProviderInfoDao = dbHelper.getDao(ProviderInfo.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void clearTable() {
		try {
			TableUtils.clearTable(mProviderInfoDao.getConnectionSource(),
					AgricultureItem.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isTableEmpty() {
		try {
			if (mProviderInfoDao.countOf() == 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addProvider(ArrayList<ProviderInfo> providers) {
		for (ProviderInfo providerInfo : providers) {
			try {
				CreateOrUpdateStatus isSaved = mProviderInfoDao
						.createOrUpdate(providerInfo);
				return isSaved.isCreated() || isSaved.isUpdated() ? true
						: false;

			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public List<ProviderInfo> getProvidersInfo() {
		try {
			return mProviderInfoDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
