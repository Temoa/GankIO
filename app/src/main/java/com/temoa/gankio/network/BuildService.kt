package com.temoa.gankio.network

import com.temoa.gankio.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Temoa
 * on 2016/8/8 19:37
 */
object BuildService {

  private var retrofit: Retrofit? = null

  val gankService: GankApi
    get() {
      if (retrofit == null) {
        val clientBuild = OkHttpClient.Builder()
        clientBuild.connectTimeout(10, TimeUnit.SECONDS)
        clientBuild.readTimeout(10, TimeUnit.SECONDS)
        retrofit = Retrofit.Builder()
            .client(clientBuild.build())
            .baseUrl(Constants.GANK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
      }
      return retrofit!!.create(GankApi::class.java)
    }
}