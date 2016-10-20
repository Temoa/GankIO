package com.temoa.gankio.callback;

/**
 * Created by Temoa
 * on 2016/8/2 14:08
 */
public interface ICallback<T> {

    void onSucceed(String type, String key, T t);

    void onError(String type, String key, String error);
}
