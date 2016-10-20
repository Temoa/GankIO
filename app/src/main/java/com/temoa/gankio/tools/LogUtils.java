package com.temoa.gankio.tools;

import android.util.Log;

/**
 * Created by Temoa
 * on 2016/8/8 20:20
 */
public class LogUtils {

    // if this variable is true, the log will show on release version.
    protected static boolean showLog = true;

    // default tag
    private static String TAG = "DEFAULT";

    // ----default tag-----------------------------
    public static void v(String log) {
        if (!showLog) return;
        Log.v(TAG, log);
    }

    public static void d(String log) {
        if (!showLog) return;
        Log.d(TAG, log);
    }

    public static void i(String log) {
        if (!showLog) return;
        Log.i(TAG, log);
    }

    public static void w(String log) {
        if (!showLog) return;
        Log.w(TAG, log);
    }

    public static void e(String log) {
        if (!showLog) return;
        Log.e(TAG, log);
    }

    // ----user tag-----------------------------
    public static void v(String tag, String log) {
        if (!showLog) return;
        Log.v(tag, log);
    }

    public static void d(String tag, String log) {
        if (!showLog) return;
        Log.d(tag, log);
    }

    public static void i(String tag, String log) {
        if (!showLog) return;
        Log.i(tag, log);
    }

    public static void w(String tag, String log) {
        if (!showLog) return;
        Log.w(tag, log);
    }

    public static void e(String tag, String log) {
        if (!showLog) return;
        Log.e(tag, log);
    }
}
