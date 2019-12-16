package com.temoa.gankio.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.temoa.gankio.Constants
import com.temoa.gankio.R
import com.temoa.gankio.bean.NewGankData.Results

/**
 * Created by Temoa
 * on 2016/8/1 17:23
 */
class RecyclerAdapter(private val mContext: Context, recyclerView: RecyclerView, data: ArrayList<Results>?) : RecyclerView.Adapter<RecyclerHolder>() {

  private var realData: ArrayList<Results> = ArrayList()
  private var mItemClickListener: ItemClickListener? = null
  private var mItemChildClickListener: ItemChildClickListener? = null
  private var mItemLongClickListener: ItemLongClickListener? = null
  private var mLoadMoreListener: LoadMoreListener? = null
  private var isTypeReplaceAuthor = false
  private var isLoadMore = false
  private var isScrollDown = false

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
    val inflater = LayoutInflater.from(mContext)
    val root = inflater.inflate(R.layout.common_item, parent, false)
    return RecyclerHolder(root)
  }

  override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
    val result = realData[position]
    holder.setText(R.id.item_title, result.desc)
    holder.setText(R.id.item_author, result.who)
    val url = result.url
    if (result.images != null && result.images!!.isNotEmpty()) {
      holder.setImageByUrl(R.id.item_img, mContext, formatUrl(result.images!![0]))
    } else if (url!!.contains(Constants.TYPE_GITHUB)) {
      holder.setImageResource(R.id.item_img, R.drawable.github)
    } else if (url.contains(Constants.TYPE_CSDN)) {
      holder.setImageResource(R.id.item_img, R.drawable.csdn)
    } else if (url.contains(Constants.TYPE_JIANSHU)) {
      holder.setImageResource(R.id.item_img, R.drawable.jianshu)
    } else {
      holder.setImageResource(R.id.item_img, R.drawable.other)
    }
    val tv = holder.getView<TextView>(R.id.item_author)
    typeReplaceAuthor(isTypeReplaceAuthor, tv!!, result.type)
    val img = holder.getView<ImageView>(R.id.item_img)
    img!!.setOnClickListener(getOnClickListener(TYPE_CHILD_CLICK, position))
    holder.itemView.setOnClickListener(getOnClickListener(TYPE_ITEM_CLICK, position))
    holder.itemView.setOnLongClickListener(getOnLongClickListener(position))
  }

  override fun getItemCount(): Int {
    return realData.size
  }

  private fun getOnClickListener(type: Int, position: Int): View.OnClickListener {
    return View.OnClickListener { view ->
      when (type) {
        TYPE_ITEM_CLICK -> if (mItemClickListener != null) mItemClickListener!!.onItemClick(view, realData[position], position)
        TYPE_CHILD_CLICK -> if (mItemChildClickListener != null) mItemChildClickListener!!.onItemChildClick(view, realData[position], position)
      }
    }
  }

  private fun getOnLongClickListener(position: Int): OnLongClickListener {
    return OnLongClickListener { view ->
      if (mItemLongClickListener != null) mItemLongClickListener!!.onItemLongClick(view, realData[position], position)
      true
    }
  }

  private fun formatUrl(origin: String): String {
    val data = "?imageView2/0/w/100"
    return origin + data
  }

  fun setNewData(data: ArrayList<Results>) {
    realData = data
    notifyDataSetChanged()
  }

  fun addData(data: ArrayList<Results>) {
    val originalSize = realData.size
    realData.addAll(data)
    notifyItemRangeInserted(originalSize, data.size)
  }

  fun addItemClickListener(l: ItemClickListener?) {
    mItemClickListener = l
  }

  fun addItemChildClickListener(l: ItemChildClickListener?) {
    mItemChildClickListener = l
  }

  fun addItemLongClickListener(l: ItemLongClickListener?) {
    mItemLongClickListener = l
  }

  fun setLoadMord(value: Boolean) {
    isLoadMore = value
  }

  fun addLoadMoreListener(l: LoadMoreListener?) {
    mLoadMoreListener = l
  }

  fun isTypeReplaceAuthor(value: Boolean) {
    isTypeReplaceAuthor = value
  }

  @SuppressLint("SetTextI18n")
  private fun typeReplaceAuthor(value: Boolean, tv: TextView, type: String?) {
    if (value) {
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
  }

  interface ItemClickListener {
    fun onItemClick(v: View?, data: Results, position: Int)
  }

  interface ItemChildClickListener {
    fun onItemChildClick(v: View?, data: Results, position: Int)
  }

  interface ItemLongClickListener {
    fun onItemLongClick(v: View?, data: Results, position: Int)
  }

  interface LoadMoreListener {
    fun onLoadMore()
  }

  companion object {
    private const val TYPE_ITEM_CLICK = 0
    private const val TYPE_CHILD_CLICK = 1
  }

  init {
    realData = data ?: ArrayList()
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        isScrollDown = dy > 0
      }

      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          val lastVisibilityItemPos: Int
          val layoutManager = recyclerView.layoutManager
          lastVisibilityItemPos = if (layoutManager is LinearLayoutManager) {
            layoutManager.findLastVisibleItemPosition()
          } else {
            throw RuntimeException("Unsupported LayoutManager used")
          }
          val totalItemCount = layoutManager.getItemCount()
          if (lastVisibilityItemPos >= totalItemCount - 2 && isScrollDown && isLoadMore) {
            if (mLoadMoreListener != null) mLoadMoreListener!!.onLoadMore()
          }
        }
      }
    })
  }
}