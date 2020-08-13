package com.temoa.gankio.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.temoa.gankio.bean.GankData
import com.temoa.gankio.data.local.AppDatabase
import com.temoa.gankio.data.local.FavEntity
import com.temoa.gankio.data.mapper.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by lai
 * on 2020/8/13.
 */
class FavLocalRepository(
    private val database: AppDatabase,
    private val mapper2GankData: Mapper<FavEntity, GankData>,
    private val mapper2Fav: Mapper<GankData, FavEntity>
) : FavRepository {

  private val pagingConfig = PagingConfig(
      pageSize = 10,
      enablePlaceholders = false,
      prefetchDistance = 3,
      initialLoadSize = 20,
      maxSize = 10 * 10
  )

  override fun getData(): Flow<PagingData<GankData>> {
    return Pager(pagingConfig) { database.favDao().queryAllData() }
        .flow.map { pagingData ->
          pagingData.map { mapper2GankData.map(it) }
        }
  }

  override suspend fun insert(gankData: GankData) {
    database.favDao().insert(mapper2Fav.map(gankData))
  }

  override suspend fun delete(gankData: GankData) {
    database.favDao().delete(mapper2Fav.map(gankData))
  }
}