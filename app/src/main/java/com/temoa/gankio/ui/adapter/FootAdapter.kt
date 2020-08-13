package com.temoa.gankio.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.temoa.gankio.databinding.RvItemFootBinding

/**
 * Created by lai
 * on 2020/8/13.
 */
class FootAdapter : LoadStateAdapter<FootAdapter.ViewHolder>() {

  inner class ViewHolder(private val viewBinding: RvItemFootBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(data: LoadState) {
      viewBinding.apply {
        // 正在加载，显示进度条
        footProgressBar.visibility = if (data is LoadState.Loading) View.VISIBLE else View.GONE

        // 加载失败，显示并点击重试按钮
        footRetryBtn.visibility = if (data is LoadState.Error) View.VISIBLE else View.GONE
        footRetryBtn.setOnClickListener { }

        // 加载失败显示错误原因
        footErrorMsg.visibility = if (!(data as? LoadState.Error)?.error?.message.isNullOrBlank()) View.VISIBLE else View.GONE
        footErrorMsg.text = (data as? LoadState.Error)?.error?.message
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
    return ViewHolder(RvItemFootBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
    holder.bind(loadState)
  }
}