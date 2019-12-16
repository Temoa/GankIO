package com.temoa.gankio.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Created by Temoa
 * on 2016/8/1 18:28
 */
class RecyclerHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

  private val allViews: SparseArray<View?> = SparseArray(8)

  fun <T : View?> getView(viewId: Int): T? {
    var view = allViews[viewId]
    if (view == null) {
      view = itemView.findViewById(viewId)
      allViews.put(viewId, view)
    }
    return view as T?
  }

  fun setText(viewId: Int, text: String?): RecyclerHolder {
    val tv = getView<TextView>(viewId)!!
    tv.text = text
    return this
  }

  fun setImageResource(viewId: Int, drawableId: Int): RecyclerHolder {
    val iv = getView<ImageView>(viewId)!!
    iv.setImageResource(drawableId)
    return this
  }

  fun setImageBitmap(viewId: Int, bitmap: Bitmap?): RecyclerHolder {
    val iv = getView<ImageView>(viewId)!!
    iv.setImageBitmap(bitmap)
    return this
  }

  fun setImageByUrl(viewId: Int, context: Context?, url: String?): RecyclerHolder {
    val iv = getView<ImageView>(viewId)!!
    Glide.with(context).load(url).asBitmap().dontAnimate().centerCrop().into(iv)
    return this
  }

}