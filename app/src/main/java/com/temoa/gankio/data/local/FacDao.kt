package com.temoa.gankio.data.local

import androidx.paging.PagingSource
import androidx.room.*

/**
 * Created by lai
 * on 2020/8/13.
 */
@Dao
interface FacDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(vararg fav: FavEntity)

  @Delete
  suspend fun delete(fav: FavEntity)

  @Query("SELECT * FROM fav ORDER BY `index` DESC")
  fun queryAllData(): PagingSource<Int, FavEntity>
}