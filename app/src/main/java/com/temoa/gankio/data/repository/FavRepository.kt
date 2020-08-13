package com.temoa.gankio.data.repository

import androidx.paging.PagingData
import com.temoa.gankio.bean.GankData
import kotlinx.coroutines.flow.Flow

/**
 * Created by lai
 * on 2020/8/13.
 */
interface FavRepository {

  fun getData(): Flow<PagingData<GankData>>

  suspend fun insert(gankData: GankData)

  suspend fun delete(gankData: GankData)
}