package com.krishighar.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.krishighar.db.models.Crop;

public class CropDbHelper {
	private Dao<Crop, Integer> mDao;

	public CropDbHelper(Context ctx) {
		DbHelper dbHelper = (DbHelper) OpenHelperManager.getHelper(ctx,
				DbHelper.class);
		try {
			this.mDao = dbHelper.getDao(Crop.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void clearTable() {
		try {
			TableUtils.clearTable(mDao.getConnectionSource(), Crop.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addCrops(List<Crop> crops) {
		try {
			TableUtils.clearTable(mDao.getConnectionSource(), Crop.class);
			for (Crop crop : crops) {
				mDao.create(crop);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public List<Crop> getCrops() {
		try {
			return mDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Crop>();
		}
	}

	public List<String> getTags() {
		List<String> tags = new ArrayList<String>();
		try {
			List<Crop> crops = mDao.queryBuilder()
					.selectColumns(Crop.COLUMN_CROP_TAG).query();
			for (Crop crop : crops) {
				tags.add(crop.getTag());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tags;
	}
}
