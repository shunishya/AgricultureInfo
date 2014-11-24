package com.krishighar.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.table.TableUtils;
import com.krishighar.api.models.PushedInfo;
import com.krishighar.db.models.Info;
import com.krishighar.db.models.InfoTag;

public class InfoDbHelper {
	private Dao<Info, Integer> mDao;
	private Dao<InfoTag, Integer> mInfoTagDao;
	private Context mContext;

	public InfoDbHelper(Context ctx) {
		DbHelper dbHelper = (DbHelper) OpenHelperManager.getHelper(ctx,
				DbHelper.class);
		this.mContext = ctx;
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
			infoTagRow.setId(String.valueOf(info.getId()) + tag);
			try {
				mDao.createOrUpdate(info);
				mInfoTagDao.createOrUpdate(infoTagRow);

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
		try {
			mDao.createOrUpdate(row);
			List<String> tags = getAllTags();
			for (String tag : tags) {
				if (gcmPushedInfo.getTags().contains(tag)) {
					InfoTag infoTagRow = new InfoTag();
					infoTagRow.setInfo_id(info.getId());
					infoTagRow.setTag(tag);
					infoTagRow.setId(String.valueOf(info.getId()) + tag);
					mInfoTagDao.createOrUpdate(infoTagRow);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<String> getAllTags() {
		AgricultureItemDbHelper cropDbHelper = new AgricultureItemDbHelper(mContext);
		return cropDbHelper.getTags();
	}

	public ArrayList<Info> getAllInfo(String tag, int startRow) {
		ArrayList<Info> infos = new ArrayList<Info>();
		List<Info> cropInfo = getInfoOfCrop(tag, startRow);
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
		return infos;
	}

	public ArrayList<Info> getInfoOfCrop(String tag, int startRow) {
		ArrayList<Info> infos = new ArrayList<Info>();
		try {
			GenericRawResults<Info> resultRaw = mDao
					.queryRaw(
							"SELECT DISTINCT a.titleEn,a.bodyEn,a.timestamp,a.infoFrom,a.titleNp,a.bodyNp,a.id FROM _info a, _info_tag b WHERE b.tag='"
									+ tag
									+ "' AND a.id=b.info_id ORDER BY a.timestamp DESC LIMIT 10 OFFSET "
									+ startRow, new RawRowMapper<Info>() {
								public Info mapRow(String[] columnNames,
										String[] resultColumns) {
									return new Info(resultColumns[0],
											resultColumns[1],
											Long.parseLong(resultColumns[2]),
											resultColumns[3], resultColumns[4],
											resultColumns[5], Integer
													.parseInt(resultColumns[6]));
								}
							});
			for (Info info : resultRaw) {
				infos.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infos;
	}

}
