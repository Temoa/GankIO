package com.temoa.gankio.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator
import com.temoa.gankio.R
import com.temoa.gankio.bean.NewGankData.Results
import com.temoa.gankio.business.GankConstant
import com.temoa.gankio.business.GankViewModel
import com.temoa.gankio.tools.ToastUtils.show
import com.temoa.gankio.ui.BaseFragment
import com.temoa.gankio.ui.adapter.RecyclerAdapter

/**
 * Created by Temoa
 * on 2016/8/1 16:46
 */
class DetailFragment : BaseFragment() {

  private var mContext: Activity? = null
  private var mRecyclerView: RecyclerView? = null
  private var mRecyclerAdapter: RecyclerAdapter? = null
  private var mRefreshLayout: SwipeRefreshLayout? = null
  private var mGankViewModel: GankViewModel? = null
  private var pageIndex = 2
  private var type: String? = null
  fun getInstance(type: String?): DetailFragment {
    this.type = type
    return this
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.fragment_main, container, false)
    }
    mRecyclerView = rootView!!.findViewById(R.id.fragment_recycler)
    mRefreshLayout = rootView!!.findViewById(R.id.fragment_swipeLayout)
    if (savedInstanceState != null && type == null) {
      type = savedInstanceState.getString(BUNDLE_SAVE_KEY)
    }
    mContext = activity
    loadViewModel()
    initRecycler()
    initSwipeLayout()
    return rootView
  }

  override fun onFragmentVisibleChange(isVisible: Boolean) {
    super.onFragmentVisibleChange(isVisible)
    if (isVisible) {
      if (type != null) mGankViewModel!!.getGank(type!!, GankConstant.FLAG_DATA_NEW)
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    if (type != null) outState.putString(BUNDLE_SAVE_KEY, type)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    if (mRecyclerView != null) mRecyclerView!!.adapter = null
  }

  private fun initRecycler() {
    mRecyclerView!!.layoutManager = LinearLayoutManager(mContext)
    mRecyclerView!!.addItemDecoration(MaterialViewPagerHeaderDecorator())
    mRecyclerView!!.itemAnimator = DefaultItemAnimator()
    mRecyclerAdapter = RecyclerAdapter(mContext!!, mRecyclerView!!, null)
    mRecyclerAdapter!!.addItemClickListener(object : RecyclerAdapter.ItemClickListener {
      override fun onItemClick(v: View?, data: Results, position: Int) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(data.url)
        startActivity(intent)
      }
    })
    mRecyclerAdapter!!.addItemChildClickListener(object : RecyclerAdapter.ItemChildClickListener {
      override fun onItemChildClick(v: View?, data: Results, position: Int) {
        saveFavDataToDB(data)
      }
    })
    mRecyclerAdapter!!.setLoadMord(true)
    mRecyclerAdapter!!.addLoadMoreListener(object : RecyclerAdapter.LoadMoreListener {
      override fun onLoadMore() {
        if (type != null) mGankViewModel!!.getMoreGank(type!!, pageIndex)
      }
    })
    mRecyclerView!!.adapter = mRecyclerAdapter
  }

  private fun loadViewModel() {
    mGankViewModel = ViewModelProviders.of(this).get(GankViewModel::class.java)

    mGankViewModel!!.gankData.observe(this, Observer { results ->
      if (mRefreshLayout != null && mRefreshLayout!!.isRefreshing) {
        mRefreshLayout!!.isRefreshing = false
      }
      if (results == null) {
        show(mContext, "请求失败")
        return@Observer
      }
      if (mRecyclerAdapter != null) {
        mRecyclerAdapter!!.setNewData(results as ArrayList<Results>)
      }
      pageIndex = 2
    })

    mGankViewModel!!.moreGankData.observe(this, Observer { results ->
      if (mRefreshLayout != null && mRefreshLayout!!.isRefreshing) {
        mRefreshLayout!!.isRefreshing = false
      }
      if (results == null) {
        show(mContext, "请求失败")
        return@Observer
      }
      if (mRecyclerAdapter != null) {
        mRecyclerAdapter!!.addData(results as ArrayList<Results>)
        pageIndex++
      }
    })
    mGankViewModel!!.saveDataResult.observe(this, Observer { aBoolean -> show(mContext, if (aBoolean) getString(R.string.saved) else "失败") })
    if (type != null) mGankViewModel!!.getGank(type!!, GankConstant.FLAG_DATA_CACHE)
  }

  private fun initSwipeLayout() {
    mRefreshLayout!!.setOnRefreshListener { if (type != null) mGankViewModel!!.getGank(type!!, GankConstant.FLAG_DATA_NEW) }
  }

  private fun saveFavDataToDB(data: Results) {
    if (type != null) mGankViewModel!!.saveGank2Db(data)
  }

  companion object {
    private const val BUNDLE_SAVE_KEY = "type"
  }
}