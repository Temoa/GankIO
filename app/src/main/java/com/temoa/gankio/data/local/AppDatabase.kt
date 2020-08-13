package com.temoa.gankio.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by lai
 * on 2020/8/13.
 */

@Database(
    entities = [FavEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun favDao(): FacDao

  companion object {
    private var instance: AppDatabase? = null

    @Synchronized
    fun init(context: Context): AppDatabase? {
      if (instance == null) {
        instance = Room
            .databaseBuilder(
                context, AppDatabase::class.java, "gank.db")
            .build()
      }
      return instance
    }
  }
}