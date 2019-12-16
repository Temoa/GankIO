package com.temoa.gankio.tools

import android.util.Log

/**
 * Created by Temoa
 * on 2016/8/8 20:20
 */
object LogUtils {

  // if this variable is true, the log will show on release version.
  private var showLog = true
  // default tag
  private const val TAG = "DEFAULT"

  // ----default tag-----------------------------
  fun v(log: String) {
    if (!showLog) return
    Log.v(TAG, log)
  }

  fun d(log: String) {
    if (!showLog) return
    Log.d(TAG, log)
  }

  fun i(log: String) {
    if (!showLog) return
    Log.i(TAG, log)
  }

  fun w(log: String) {
    if (!showLog) return
    Log.w(TAG, log)
  }

  fun e(log: String) {
    if (!showLog) return
    Log.e(TAG, log)
  }

  // ----user tag-----------------------------
  fun v(tag: String, log: String) {
    if (!showLog) return
    Log.v(tag, log)
  }

  fun d(tag: String, log: String) {
    if (!showLog) return
    Log.d(tag, log)
  }

  fun i(tag: String, log: String) {
    if (!showLog) return
    Log.i(tag, log)
  }

  fun w(tag: String, log: String) {
    if (!showLog) return
    Log.w(tag, log)
  }

  fun e(tag: String, log: String) {
    if (!showLog) return
    Log.e(tag, log)
  }
}