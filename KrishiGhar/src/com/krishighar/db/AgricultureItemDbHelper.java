package com.krishighar.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.krishighar.db.models.AgricultureItem;

public class AgricultureItemDbHelper {
	private Dao<AgricultureItem, Integer> mDao;

	public AgricultureItemDbHelper(Context ctx) {
		DbHelper dbHelper = (DbHelper) OpenHelperManager.getHelper(ctx,
				DbHelper.class);
		try {
			this.mDao = dbHelper.getDao(AgricultureItem.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void clearTable() {
		try {
			TableUtils.clearTable(mDao.getConnectionSource(),
					AgricultureItem.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addItem(List<AgricultureItem> items) {
		try {
			for (AgricultureItem crop : items) {
				mDao.create(crop);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public List<AgricultureItem> getItems() {
		try {
			return mDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<AgricultureItem>();
		}
	}

	public List<String> getTags() {
		List<String> tags = new ArrayList<String>();
		try {
			List<AgricultureItem> items = mDao.queryForAll();
			for (AgricultureItem item : items) {
				tags.add(item.getTag());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tags;
	}
}
