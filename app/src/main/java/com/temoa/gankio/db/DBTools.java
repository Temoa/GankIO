package com.temoa.gankio.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.temoa.gankio.bean.NewGankData;
import com.temoa.gankio.Constants;
import com.temoa.gankio.tools.LogUtils;

import java.util.ArrayList;

/**
 * Created by Temoa
 * on 2016/8/11 20:41
 */
public class DBTools {

    public static boolean insert(DBHelper dbHelper, NewGankData.Results data) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(Constants.DB_ITEM_DESC, data.getDesc());
            values.put(Constants.DB_ITEM_TYPE, data.getType());
            values.put(Constants.DB_ITEM_URL, data.getUrl());
            db.insert(Constants.DB_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteConstraintException e) {
            LogUtils.e("DBTools -> insert", "insert data error");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public static ArrayList<NewGankData.Results> queryByType(DBHelper dbHelper, String type) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Constants.DB_TABLE_NAME, null, "type = ?", new String[]{type}, null, null, null);
        return packageData(cursor);
    }

    public static ArrayList<NewGankData.Results> queryAll(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Constants.DB_TABLE_NAME, null, null, null, null, null, null);
        return packageData(cursor);
    }

    private static ArrayList<NewGankData.Results> packageData(Cursor cursor) {
        ArrayList<NewGankData.Results> entities = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                NewGankData.Results entity = new NewGankData.Results();
                entity.setDesc(cursor.getString(cursor.getColumnIndex(Constants.DB_ITEM_DESC)));
                entity.setType(cursor.getString(cursor.getColumnIndex(Constants.DB_ITEM_TYPE)));
                entity.setUrl(cursor.getString(cursor.getColumnIndex(Constants.DB_ITEM_URL)));
                entities.add(entity);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return entities;
    }

    public static void deleteByDesc(DBHelper dbHelper, String desc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(Constants.DB_TABLE_NAME, "desc = ?", new String[]{desc});
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            LogUtils.e("DBTools -> delete", "delete data error");
        } finally {
            db.endTransaction();
        }
    }
}
