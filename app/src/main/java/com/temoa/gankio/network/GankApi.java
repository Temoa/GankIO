package com.temoa.gankio.network;

import com.temoa.gankio.bean.NewGankData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Temoa
 * on 2016/8/2 13:45
 */
public interface GankApi {

    @GET("{type}/{count}/{pageIndex}")
    Observable<NewGankData> getNewData(
            @Path("type") String type,
            @Path("count") int count,
            @Path("pageIndex") int pageIndex
    );

    @GET("福利/3/1")
    Observable<NewGankData> getBeauty();
}
