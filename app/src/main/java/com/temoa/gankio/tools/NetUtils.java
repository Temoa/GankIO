package com.temoa.gankio.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Temoa
 * on 2016/8/8 22:02
 */
public class NetUtils {

    /**
     * 用户是否连接网络
     * @param context Context
     */
    public static boolean isNetConnection(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable());
    }
}
