package com.temoa.gankio.network;

import com.temoa.gankio.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Temoa
 * on 2016/8/8 19:37
 */
public class BuildService {

  private static Retrofit retrofit;

  public static GankApi getGankService() {
    if (retrofit == null) {
      OkHttpClient.Builder clientBuild = new OkHttpClient.Builder();
      clientBuild.connectTimeout(10, TimeUnit.SECONDS);
      clientBuild.readTimeout(10, TimeUnit.SECONDS);
      retrofit = new Retrofit.Builder()
          .client(clientBuild.build())
          .baseUrl(Constants.GANK_BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();
    }
    return retrofit.create(GankApi.class);
  }
}
