package com.temoa.gankio.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.bumptech.glide.Glide
import com.temoa.gankio.Constants
import com.temoa.gankio.Ext.load
import com.temoa.gankio.R
import com.temoa.gankio.bean.GankData
import com.temoa.gankio.databinding.RvItemGankDataBinding

/**
 * Created by Temoa
 * on 2016/8/1 17:23
 */
class GankDataAdapter(
    private val onItemClick: (View, GankData, Int) -> Unit = { _, _, _ -> },
    private val onItemLongClick: (View, GankData, Int) -> Boolean = { _, _, _ -> true },
    private val onItemChildClick: (View, GankData, Int) -> Unit = { _, _, _ -> }
) : PagingDataAdapter<GankData, GankDataAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<GankData>() {
      override fun areItemsTheSame(oldItem: GankData, newItem: GankData) = oldItem == newItem
      override fun areContentsTheSame(oldItem: GankData, newItem: GankData) = oldItem.id == newItem.id
    }) {

  inner class ViewHolder(private val binding: RvItemGankDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: GankData): RvItemGankDataBinding {
      return binding.apply {
        gankData = data
        executePendingBindings()
      }
    }
  }

  private var isTypeReplaceAuthor = false

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.rv_item_gank_data, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val data = getItem(position) as GankData
    val binding = holder.bind(data)
    val url = data.url
    when {
      data.images.isNotEmpty() -> binding.itemImg.load(formatUrl(data.images[0]))
      url.contains(Constants.TYPE_GITHUB) -> binding.itemImg.setImageResource(R.drawable.github)
      url.contains(Constants.TYPE_CSDN) -> binding.itemImg.setImageResource(R.drawable.csdn)
      url.contains(Constants.TYPE_JIANSHU) -> binding.itemImg.setImageResource(R.drawable.jianshu)
      else -> binding.itemImg.setImageResource(R.drawable.other)
    }
    typeReplaceAuthor(binding.itemAuthor, data.type)

    binding.itemImg.setOnClickListener {
      onItemChildClick(it, data, holder.bindingAdapterPosition)
    }

    holder.itemView.setOnClickListener {
      onItemClick(it, data, holder.bindingAdapterPosition)
    }

    holder.itemView.setOnLongClickListener {
      onItemLongClick(it, data, holder.bindingAdapterPosition)
    }
  }

  fun isTypeReplaceAuthor(value: Boolean) {
    isTypeReplaceAuthor = value
  }

  @SuppressLint("SetTextI18n")
  private fun typeReplaceAuthor(tv: TextView, type: String?) {
    if (!isTypeReplaceAuthor) return
    when (type) {
      Constants.TYPE_ANDROID -> {
        tv.text = "  " + Constants.TYPE_ANDROID + "  "
        tv.setBackgroundColor(Color.parseColor("#8BC34A"))
        tv.setTextColor(Color.WHITE)
      }
      Constants.TYPE_IOS -> {
        tv.text = "  " + Constants.TYPE_IOS + "  "
        tv.setBackgroundColor(Color.parseColor("#212121"))
        tv.setTextColor(Color.WHITE)
      }
      Constants.TYPE_WEB -> {
        tv.text = "  " + Constants.TYPE_WEB + "  "
        tv.setBackgroundColor(Color.parseColor("#F57C00"))
        tv.setTextColor(Color.WHITE)
      }
    }
  }

  private fun formatUrl(origin: String): String {
    val data = "?imageView2/0/w/50"
    return origin + data
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        when (newState) {
          SCROLL_STATE_IDLE -> Glide.with(recyclerView.context).resumeRequests()
          else -> Glide.with(recyclerView.context).pauseRequests()
        }
      }
    })
  }
}