package com.temoa.gankio.model;

import com.temoa.gankio.constants.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Temoa
 * on 2016/8/8 19:37
 */
public class BuildService {

    private static Retrofit retrofit;

    public static GankService getGankService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    // 设置base访问地址
                    .baseUrl(Constants.GANK_BASE_URL)
                    // 设置Json解析库Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(GankService.class);
    }
}
