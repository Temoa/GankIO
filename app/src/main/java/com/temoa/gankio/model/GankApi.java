package com.temoa.gankio.model;

import com.temoa.gankio.bean.GankData;
import com.temoa.gankio.callback.ICallback;
import com.temoa.gankio.tools.LogUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Temoa
 * on 2016/8/2 13:47
 */
public class GankApi {

    public static Call<GankData> getDataFromGank(final String type, int count, int pageIndex, final ICallback<GankData> callback) {
        Call<GankData> callData = BuildService.getGankService().get(type, count, pageIndex);
        final String key = type + count + pageIndex;
        callData.enqueue(new retrofit2.Callback<GankData>() {
            @Override
            public void onResponse(Call<GankData> call, Response<GankData> response) {
                if (response.isSuccessful()) {
                    GankData data = response.body();
                    LogUtils.d("getDataFromGank -> onResponse", data.toString());
                    if (!data.isError()) {
                        callback.onSucceed(type, key, data);
                    } else {
                        callback.onError(type, key, "数据加载出错,可能服务器抽风");
                    }
                }
            }

            @Override
            public void onFailure(Call<GankData> call, Throwable t) {
                LogUtils.e("getDataFromGank -> onFailure", t.toString());
                callback.onError(type, key, "获取数据出错");
            }
        });
        return callData;
    }
}
