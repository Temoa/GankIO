package com.temoa.gankio.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.temoa.gankio.Constants
import com.temoa.gankio.bean.NewGankData.Results
import com.temoa.gankio.tools.LogUtils
import java.util.*

/**
 * Created by Temoa
 * on 2016/8/11 20:41
 */
object DBTools {

  fun insert(dbHelper: DBHelper, data: Results): Boolean {
    val db = dbHelper.writableDatabase
    db.beginTransaction()
    return try {
      val values = ContentValues()
      values.put(Constants.DB_ITEM_DESC, data.desc)
      values.put(Constants.DB_ITEM_TYPE, data.type)
      values.put(Constants.DB_ITEM_URL, data.url)
      db.insert(Constants.DB_TABLE_NAME, null, values)
      db.setTransactionSuccessful()
      true
    } catch (e: SQLiteConstraintException) {
      LogUtils.e("DBTools -> insert", "insert data error")
      false
    } finally {
      db.endTransaction()
    }
  }

  fun queryByType(dbHelper: DBHelper, type: String): ArrayList<Results> {
    val db = dbHelper.readableDatabase
    val cursor = db.query(Constants.DB_TABLE_NAME, null, "type = ?", arrayOf(type), null, null, null)
    return packageData(cursor)
  }

  @JvmStatic
  fun queryAll(dbHelper: DBHelper): ArrayList<Results> {
    val db = dbHelper.readableDatabase
    val cursor = db.query(Constants.DB_TABLE_NAME, null, null, null, null, null, null)
    return packageData(cursor)
  }

  private fun packageData(cursor: Cursor): ArrayList<Results> {
    val entities = ArrayList<Results>()
    if (cursor.moveToFirst()) {
      do {
        val entity = Results()
        entity.desc = cursor.getString(cursor.getColumnIndex(Constants.DB_ITEM_DESC))
        entity.type = cursor.getString(cursor.getColumnIndex(Constants.DB_ITEM_TYPE))
        entity.url = cursor.getString(cursor.getColumnIndex(Constants.DB_ITEM_URL))
        entities.add(0, entity)
      } while (cursor.moveToNext())
    }
    cursor.close()
    return entities
  }

  @JvmStatic
  fun deleteByDesc(dbHelper: DBHelper, desc: String) {
    val db = dbHelper.writableDatabase
    db.beginTransaction()
    try {
      db.delete(Constants.DB_TABLE_NAME, "desc = ?", arrayOf(desc))
      db.setTransactionSuccessful()
    } catch (e: SQLiteException) {
      LogUtils.e("DBTools -> delete", "delete data error")
    } finally {
      db.endTransaction()
    }
  }
}