package com.temoa.bellezza.model;

import com.temoa.bellezza.bean.GankAndroidTipsBean;
import com.temoa.bellezza.bean.GankBeaWelfareTipsBean;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface IGankAPIService {

    @GET("/api/data/Android/{amount}/{page}")
    Call<GankAndroidTipsBean> getData(@Path("amount") int amount ,@Path("page") int page);

    @GET("/api/data/福利/{amount}/{page}")
    Call<GankBeaWelfareTipsBean> getImg(@Path("amount") int amount,@Path("page") int page);
}
