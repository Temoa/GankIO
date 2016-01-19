package com.temoa.bellezza.model;

import android.os.AsyncTask;

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
import java.util.concurrent.TimeUnit;

public class GankAndroidTipsDataImpl implements GankAndroidTipsData {

    @Override
    public void loadAndroidTipsData(final OnFinishedListener listener) {
        final OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(1, TimeUnit.MINUTES);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                Request request = new Request.Builder().url(API.URL.GAKN_ANDROIDTIPS).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
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
                GankAndroidTipsBean gankAndroidTipsBean = JSON.parseObject(s,GankAndroidTipsBean.class);
                List<String> titleItem = new ArrayList<>();
                for (int i = 0; i < gankAndroidTipsBean.getResults().size(); i++) {
                    titleItem.add(gankAndroidTipsBean.getResults().get(i).getDesc());
                }
                listener.onFinished(titleItem);
            }
        }.execute();
    }
}
