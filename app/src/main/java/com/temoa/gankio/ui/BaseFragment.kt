package com.temoa.gankio.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * Created by Temoa
 * on 2016/11/9 10:30
 */
abstract class BaseFragment : Fragment() {

  private var hasCreateView = false
  private var isFragmentVisible = false
  @JvmField
  protected var rootView: View? = null

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (rootView == null) {
      return
    }
    hasCreateView = true
    if (isVisibleToUser) {
      onFragmentVisibleChange(true)
      isFragmentVisible = true
      return
    }
    if (isFragmentVisible) {
      onFragmentVisibleChange(false)
      isFragmentVisible = false
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (!hasCreateView && userVisibleHint) {
      onFragmentVisibleChange(true)
      isFragmentVisible = true
    }
  }

  protected open fun onFragmentVisibleChange(isVisible: Boolean) {}
}