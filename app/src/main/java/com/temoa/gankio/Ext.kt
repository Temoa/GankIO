package com.temoa.gankio

import android.app.Activity
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

/**
 * Created by lai
 * on 2020/8/12.
 */
object Ext {

  fun ImageView.load(url: String) {
    Glide.with(context).asBitmap().load(url).into(this)
  }

  fun Activity.toast(textRes: Int) {
    Toast.makeText(this, textRes, Toast.LENGTH_SHORT).show()
  }
}