package com.temoa.gankio.data.repository

import androidx.paging.PagingData
import com.temoa.gankio.bean.GankData
import kotlinx.coroutines.flow.Flow

/**
 * Created by lai
 * on 2020/8/12.
 */
interface GankRepository {
  fun getGank(type: String): Flow<PagingData<GankData>>
}