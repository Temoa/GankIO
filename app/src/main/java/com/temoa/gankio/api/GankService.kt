package com.temoa.gankio.api

import com.temoa.gankio.Constants
import com.temoa.gankio.bean.Gank
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Temoa
 * on 2016/8/2 13:45
 */
interface GankService {

  @GET("type/{type}/page/{page}/count/{count}")
  suspend fun getGank(
      @Path("type") type: String?,
      @Path("page") page: Int,
      @Path("count") count: Int = 10
  ): Gank

  companion object {

    fun buildOkHttpClient(): OkHttpClient {
      return OkHttpClient.Builder().build()
    }

    fun create(client: OkHttpClient): GankService {
      val retrofit = Retrofit.Builder()
          .client(client)
          .baseUrl(Constants.GANK_BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()

      return retrofit.create(GankService::class.java)
    }
  }
}