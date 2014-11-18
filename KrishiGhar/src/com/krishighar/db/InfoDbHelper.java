package com.krishighar.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.table.TableUtils;
import com.krishighar.api.models.PushedInfo;
import com.krishighar.db.models.Info;
import com.krishighar.db.models.InfoTag;

public class InfoDbHelper {
	private Dao<Info, Integer> mDao;
	private Dao<InfoTag, Integer> mInfoTagDao;

	public InfoDbHelper(Context ctx) {
		DbHelper dbHelper = (DbHelper) OpenHelperManager.getHelper(ctx,
				DbHelper.class);
		try {
			this.mDao = dbHelper.getDao(Info.class);
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

	public void clearTable() {
		try {
			TableUtils.clearTable(mDao.getConnectionSource(), Info.class);
			TableUtils.clearTable(mInfoTagDao.getConnectionSource(),
					InfoTag.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addInfo(ArrayList<Info> infos, String tag) {

		for (Info info : infos) {
			InfoTag infoTagRow = new InfoTag();
			infoTagRow.setInfo_id(info.getId());
			infoTagRow.setTag(tag);
			try {
				CreateOrUpdateStatus status = mDao.createOrUpdate(info);
				if (status.isCreated()) {
					mInfoTagDao.createOrUpdate(infoTagRow);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void addInfo(PushedInfo gcmPushedInfo) {
		Info row = new Info();
		Info info = gcmPushedInfo.getInfo();
		row.setBodyEn(info.getBodyEn());
		row.setBodyNp(info.getBodyNp());
		row.setInfoFrom(info.getInfoFrom());
		row.setId(info.getId());
		row.setTimestamp(info.getTimestamp());
		row.setTitleEn(info.getTitleEn());
		row.setTitleNp(info.getTitleNp());

		for (String tag : gcmPushedInfo.getTags()) {
			InfoTag infoTagRow = new InfoTag();
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

	@SuppressWarnings("deprecation")
	public ArrayList<Info> getAllInfo(String tag, int startRow) {
		ArrayList<Info> infos = new ArrayList<Info>();
		List<Info> cropInfo = new ArrayList<Info>();
		try {
			List<InfoTag> infoId = mInfoTagDao.queryBuilder().offset(startRow)
					.limit(4).where().eq(InfoTag.COLUMN_TAG, tag).query();
			for (InfoTag id : infoId) {
				cropInfo = mDao.queryBuilder().where()
						.eq(Info.COLUMN_ID, id.getInfo_id()).query();
				for (Info infoTable : cropInfo) {
					Info info = new Info();
					info.setBodyEn(infoTable.getBodyEn());
					info.setBodyNp(infoTable.getBodyNp());
					info.setInfoFrom(infoTable.getInfoFrom());
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
