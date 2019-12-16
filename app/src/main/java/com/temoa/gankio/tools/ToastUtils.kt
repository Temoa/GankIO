package com.temoa.gankio.tools

import android.app.Activity
import android.widget.Toast

/**
 * Created by Temoa
 * on 2016/8/8 20:00
 */
object ToastUtils {

  private var mToast: Toast? = null
  /**
   * @param context context
   * @param msg     显示的内容
   */
  @JvmStatic
  fun show(context: Activity?, msg: String?) { // 判断是否在主线程上
    if (mToast != null) {
      mToast?.setText(msg)
      mToast?.duration = Toast.LENGTH_SHORT
      mToast?.show()
    } else {
      mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
      mToast?.show()
    }
  }
}