package com.temoa.gankio.model;

import com.temoa.gankio.bean.GankData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Temoa
 * on 2016/8/2 13:45
 */
public interface GankService {
    @GET("{type}/{count}/{pageIndex}")
    Call<GankData> get(
            @Path("type") String type,
            @Path("count") int count,
            @Path("pageIndex") int pageIndex
    );
}
