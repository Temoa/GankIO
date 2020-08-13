package com.temoa.gankio.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.temoa.gankio.api.GankService
import com.temoa.gankio.bean.GankData
import kotlinx.coroutines.flow.Flow

/**
 * Created by lai
 * on 2020/8/12.
 */
class GankRemoteRepository(private val service: GankService) : GankRepository {

  private val pagingConfig = PagingConfig(
      pageSize = 10,
      enablePlaceholders = false,
      prefetchDistance = 3,
      initialLoadSize = 20,
      maxSize = 10 * 10
  )

  override fun getGank(type: String): Flow<PagingData<GankData>> {
    return Pager(pagingConfig) { GankRemotePagingSource(service, type) }.flow
  }
}