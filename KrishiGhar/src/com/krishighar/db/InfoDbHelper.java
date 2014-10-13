package com.krishighar.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.krishighar.api.models.PushedInfo;
import com.krishighar.db.models.InfoTable;
import com.krishighar.models.Info;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InfoDbHelper {
	private Dao<InfoTable, Integer> mDao;

	public InfoDbHelper(Context ctx) {
		DbHelper dbHelper = (DbHelper) OpenHelperManager.getHelper(ctx,
				DbHelper.class);
		try {
			this.mDao = dbHelper.getDao(InfoTable.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isTableEmpty() {
		try {
			if (mDao.countOf() == 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void addInfo(ArrayList<Info> infos, String tag) {
		InfoTable row = new InfoTable();
		for (Info info : infos) {
			row.setInfoBody(info.getBody());
			row.setInfoFrom(info.getFrom());
			row.setTimestamp(info.getTimestamp());
			row.setInfoTitle(info.getTitle());
			row.setTag(tag);
			try {
				mDao.create(row);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void addInfo(PushedInfo gcmPushedInfo) {
		InfoTable row = new InfoTable();
		row.setInfoBody(gcmPushedInfo.getInfoBody());
		row.setInfoFrom(gcmPushedInfo.getInfoFrom());
		row.setInfoTitle(gcmPushedInfo.getInfoTitle());
		row.setTimestamp(gcmPushedInfo.getTimestamp());
		for (String tag : gcmPushedInfo.getTags()) {
			row.setTag(tag);
			try {
				mDao.create(row);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Info> getAllInfo(String tag) {
		ArrayList<Info> infos = new ArrayList<Info>();
		try {
			List<InfoTable> cropInfo = mDao.queryBuilder().where()
					.eq(InfoTable.TAG, tag).query();
			for (InfoTable infoTable : cropInfo) {
				Info info = new Info();
				info.setBody(infoTable.getInfoBody());
				info.setFrom(infoTable.getInfoFrom());
				info.setTimestamp(infoTable.getTimestamp());
				info.setTitle(infoTable.getInfoTitle());
				infos.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infos;
	}

}
