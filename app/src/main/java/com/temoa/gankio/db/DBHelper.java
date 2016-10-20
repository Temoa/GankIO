package com.temoa.gankio.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.temoa.gankio.tools.LogUtils;

/**
 * Created by Temoa
 * on 2016/8/11 19:14
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "default.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDB = "create table Favorites (" +
                "_id integer primary key autoincrement, " +
                "desc text unique, " +
                "type text, " +
                "url text)";
        db.execSQL(createDB);
        LogUtils.d("DBHelper", "Create Favorites table succeeded~!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeDB = "drop table if exists Favorites";
        db.execSQL(upgradeDB);
        onCreate(db);
    }
}
