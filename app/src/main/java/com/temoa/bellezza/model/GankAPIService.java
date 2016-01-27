package com.temoa.bellezza.model;

import android.util.Log;

import com.temoa.bellezza.api.API;
import com.temoa.bellezza.bean.GankAndroidTipsBean;
import com.temoa.bellezza.listener.OnFinishedListener;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class GankAPIService {

    private List<String> items = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    private IGankAPIService gankAPIService;
    Retrofit retrofit;

    public GankAPIService() {

        retrofit = new Retrofit.Builder()
                .baseUrl(API.URL.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gankAPIService = retrofit.create(IGankAPIService.class);
    }

    public void loadTipsData(int amount, int page, final OnFinishedListener listener) {
        final Call<GankAndroidTipsBean> call = gankAPIService.getData(amount, page);
        call.enqueue(new Callback<GankAndroidTipsBean>() {
            @Override
            public void onResponse(Response<GankAndroidTipsBean> response, Retrofit retrofit) {
                GankAndroidTipsBean gankAndroidTipsBean = response.body();
                items.clear();
                urls.clear();
                for (int i = 0; i < gankAndroidTipsBean.getResults().size(); i++) {
                    items.add(gankAndroidTipsBean.getResults().get(i).getDesc());
                    urls.add(gankAndroidTipsBean.getResults().get(i).getUrl());
                }
                listener.onLoadFinished(items);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("GankAPIService", "load tips data failed");
            }
        });
    }

    public void loadMoreTipsData(int amount, int page, final OnFinishedListener listener) {
        Call<GankAndroidTipsBean> call = gankAPIService.getData(amount, page);
        call.enqueue(new Callback<GankAndroidTipsBean>() {
            @Override
            public void onResponse(Response<GankAndroidTipsBean> response, Retrofit retrofit) {
                GankAndroidTipsBean gankAndroidTipsBean = response.body();
                for (int i = 0; i < gankAndroidTipsBean.getResults().size(); i++) {
                    items.add(gankAndroidTipsBean.getResults().get(i).getDesc());
                    urls.add(gankAndroidTipsBean.getResults().get(i).getUrl());
                }
                listener.onLoadMoreFinished(items);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("GankAPIService", "load tips data failed");
            }
        });
    }

    public List<String> loadTipsUrls() {
        return urls;
    }
}
