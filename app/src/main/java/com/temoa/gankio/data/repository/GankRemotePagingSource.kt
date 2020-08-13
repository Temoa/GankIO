package com.temoa.gankio.data.repository

import androidx.paging.PagingSource
import com.temoa.gankio.api.GankService
import com.temoa.gankio.bean.GankData

/**
 * Created by lai
 * on 2020/8/13.
 */
class GankRemotePagingSource(
    private val service: GankService,
    private val gankType: String)
  : PagingSource<Int, GankData>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GankData> {
    return try {
      val key = params.key ?: 1
      val item = service.getGank(gankType, key, params.loadSize)

      val currentPage = item.page
      val prevPage = if (currentPage == 1) -1 else currentPage - 1
      val nextPage = if (currentPage + 1 <= item.pageCount) currentPage + 1 else -1

      LoadResult.Page(
          data = item.gankData,
          prevKey = if (prevPage == -1) null else prevPage,
          nextKey = if (nextPage == -1) null else nextPage
      )
    } catch (e: Exception) {
      e.printStackTrace()
      LoadResult.Error(e)
    }
  }
}