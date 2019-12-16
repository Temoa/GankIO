package com.temoa.gankio.network

import com.temoa.gankio.bean.NewGankData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Temoa
 * on 2016/8/2 13:45
 */
interface GankApi {

  @GET("{type}/{count}/{pageIndex}")
  fun getNewData(
      @Path("type") type: String?,
      @Path("count") count: Int,
      @Path("pageIndex") pageIndex: Int
  ): Observable<NewGankData>

  @get:GET("福利/3/1")
  val beauty: Observable<NewGankData>
}