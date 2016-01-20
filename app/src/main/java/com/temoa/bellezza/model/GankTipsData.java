package com.temoa.bellezza.model;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.temoa.bellezza.api.API;
import com.temoa.bellezza.bean.GankAndroidTipsBean;
import com.temoa.bellezza.listener.OnFinishedListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GankTipsData implements IGankTipsData {

    private OkHttpClient client = new OkHttpClient();
    private List<String> url = new ArrayList<>();

    @Override
    public void loadTipsData(final OnFinishedListener listener) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                Request request = new Request.Builder().url(API.URL.GAKN_ANDROIDTIPS).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        return response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("API", s);
                try {
                    GankAndroidTipsBean gankAndroidTipsBean = JSON.parseObject(s, GankAndroidTipsBean.class);
                    List<String> item = new ArrayList<>();
                    url = new ArrayList<>();
                    for (int i = 0; i < gankAndroidTipsBean.getResults().size(); i++) {
                        item.add(gankAndroidTipsBean.getResults().get(i).getDesc());
                        url.add(gankAndroidTipsBean.getResults().get(i).getUrl());
                    }
                    listener.onLoadFinished(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    @Override
    public List<String> loadTipsUrl() {
        return url;
    }
}
