package com.temoa.gankio.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import com.temoa.gankio.tools.LogUtils

/**
 * Created by Temoa
 * on 2016/8/11 19:14
 */
class DBHelper private constructor(context: Context, name: String, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

  constructor(context: Context) : this(context, DB_NAME, null, DB_VERSION)

  override fun onCreate(db: SQLiteDatabase) {
    val createDB = "create table Favorites (" +
        "_id integer primary key autoincrement, " +
        "desc text unique, " +
        "type text, " +
        "url text)"
    db.execSQL(createDB)
    LogUtils.d("DBHelper", "Create Favorites table succeeded~!")
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    val upgradeDB = "drop table if exists Favorites"
    db.execSQL(upgradeDB)
    onCreate(db)
  }

  companion object {
    private const val DB_NAME = "default.db"
    private const val DB_VERSION = 1
  }
}