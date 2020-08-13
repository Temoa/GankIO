package com.temoa.gankio.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by lai
 * on 2020/8/13.
 */
@Entity(tableName = "fav")
data class FavEntity(
    val id: String,
    val title: String,
    val author: String,
    val type: String,
    val url: String
) {
  @PrimaryKey(autoGenerate = true)
  var index: Long = 0L
}