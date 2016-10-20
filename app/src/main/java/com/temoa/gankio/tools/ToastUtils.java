package com.temoa.gankio.tools;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Temoa
 * on 2016/8/8 20:00
 */
public class ToastUtils {

    private static Toast mToast;

    /**
     * @param context context
     * @param msg     显示的内容
     */
    public static void show(final Activity context, final String msg) {
        // 判断是否在主线程上
        if (mToast != null) {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }
}
