package com.krishighar.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.krishighar.db.models.Crop;
import com.krishighar.db.models.InfoTable;

public class DbHelper extends OrmLiteSqliteOpenHelper {
	public DbHelper(Context context) {
		super(context, DbConf.DATABASE_NAME, null, DbConf.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Crop.class);
			TableUtils.createTable(connectionSource, InfoTable.class);
		} catch (SQLException e) {
			Log.e("DB_HELPER", "Unable to create datbases", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			TableUtils.dropTable(connectionSource, Crop.class, true);
			TableUtils.dropTable(connectionSource, InfoTable.class, true);
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e("DB_HELPER", "Unable to upgrade database from version "
					+ oldVer + " to new " + newVer, e);
		}
	}

}
