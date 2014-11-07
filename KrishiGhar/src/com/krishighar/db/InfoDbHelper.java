package com.krishighar.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.krishighar.api.models.PushedInfo;
import com.krishighar.db.models.InfoTable;
import com.krishighar.db.models.InfoTag;
import com.krishighar.models.Info;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InfoDbHelper {
	private Dao<InfoTable, Integer> mDao;
	private Dao<InfoTag, Integer> mInfoTagDao;

	public InfoDbHelper(Context ctx) {
		DbHelper dbHelper = (DbHelper) OpenHelperManager.getHelper(ctx,
				DbHelper.class);
		try {
			this.mDao = dbHelper.getDao(InfoTable.class);
			this.mInfoTagDao = dbHelper.getDao(InfoTag.class);
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
		InfoTag infoTagRow = new InfoTag();
		for (Info info : infos) {
			row.setBodyEn(info.getBodyEn());
			row.setBodyNp(info.getBodyNp());
			row.setFrom(info.getFrom());
			row.setTimestamp(info.getTimestamp());
			row.setTitleEn(info.getTitleEn());
			row.setTitleNp(info.getTitleNp());
			row.setId(info.getId());
			infoTagRow.setId(info.getId());
			infoTagRow.setTag(tag);
			try {
				mDao.createOrUpdate(row);
				mInfoTagDao.createOrUpdate(infoTagRow);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void addInfo(PushedInfo gcmPushedInfo) {
		InfoTable row = new InfoTable();
		InfoTag infoTagRow = new InfoTag();
		for (String tag : gcmPushedInfo.getTags()) {

			infoTagRow.setTag(tag);
			try {
				mDao.createOrUpdate(row);
				mInfoTagDao.createOrUpdate(infoTagRow);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Info> getAllInfo(String tag) {
		ArrayList<Info> infos = new ArrayList<Info>();
		List<InfoTable> cropInfo = new ArrayList<InfoTable>();
		try {
			List<InfoTag> infoId = mInfoTagDao.queryBuilder().where()
					.eq(InfoTag.COLUMN_TAG, tag).query();
			for (InfoTag id : infoId) {
				cropInfo = mDao.queryBuilder().where()
						.eq(InfoTable.COLUMN_ID, id.getInfo_id()).query();
				for (InfoTable infoTable : cropInfo) {
					Info info = new Info();
					info.setBodyEn(infoTable.getBodyEn());
					info.setBodyNp(infoTable.getBodyNp());
					info.setFrom(infoTable.getFrom());
					info.setId(infoTable.getId());
					info.setTimestamp(infoTable.getTimestamp());
					info.setTitleEn(infoTable.getTitleEn());
					info.setTitleNp(infoTable.getTitleNp());
					infos.add(info);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infos;
	}

}
