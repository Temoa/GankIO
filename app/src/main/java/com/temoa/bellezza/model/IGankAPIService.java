package com.temoa.bellezza.model;

import com.temoa.bellezza.bean.GankAndroidTipsBean;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface IGankAPIService {

    @GET("/api/data/Android/{amount}/{page}")
    Call<GankAndroidTipsBean> getData(@Path("amount") int amount ,@Path("page") int page);
}
