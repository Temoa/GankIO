package com.temoa.gankio.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator
import com.temoa.gankio.Constants
import com.temoa.gankio.Ext.toast
import com.temoa.gankio.R
import com.temoa.gankio.databinding.FragmentDetailBinding
import com.temoa.gankio.ui.adapter.GankDataAdapter
import com.temoa.gankio.viewmodel.FavViewModel
import com.temoa.gankio.viewmodel.GankViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Temoa
 * on 2016/8/1 16:46
 */
class DetailFragment : LazyFragment() {

  private val mGankViewModel: GankViewModel by viewModel()
  private val mFavViewModel: FavViewModel by viewModel()

  private lateinit var mViewBinding: FragmentDetailBinding

  private lateinit var mGankDataAdapter: GankDataAdapter

  private lateinit var type: String

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    type = if (savedInstanceState != null) {
      savedInstanceState.getString(Constants.TYPE)!!
    } else {
      arguments!!.getString(Constants.TYPE, Constants.TYPE)
    }

    mViewBinding = FragmentDetailBinding.inflate(inflater).apply {
      swipeLayout.setOnRefreshListener {
        mGankDataAdapter.refresh()
      }
      detailRecycler.addItemDecoration(MaterialViewPagerHeaderDecorator())
      mGankDataAdapter = GankDataAdapter(
          onItemClick = { _, data, _ ->
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = Uri.parse(data.url)
            startActivity(intent)
          },
          onItemChildClick = { _, data, _ ->
            mFavViewModel.insert(data)
            requireActivity().toast(R.string.saved)
          }
      )
//      recycler.adapter = gankDataAdapter.withLoadStateFooter(FootAdapter())
      detailRecycler.adapter = mGankDataAdapter
    }
    mGankViewModel.typeLiveData.value = type

    return mViewBinding.root
  }

  override fun lazyInit() {
    mGankViewModel.gankLiveData.observe(viewLifecycleOwner, Observer {
      if (mViewBinding.swipeLayout.isRefreshing) mViewBinding.swipeLayout.isRefreshing = false
      mGankDataAdapter.submitData(lifecycle, it)
    })
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putString(Constants.TYPE, type)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    mViewBinding.detailRecycler.adapter = null
  }
}