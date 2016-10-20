package com.temoa.gankio;

import android.app.Application;

/**
 * Created by Temoa
 * on 2016/8/2 16:12
 */
public class MyApplication extends Application {

    private static Application mInstance;

    public static Application getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
