package com.temoa.gankio.ui.fragment

import androidx.fragment.app.Fragment

/**
 * Created by lai
 * on 2020/8/13.
 */
abstract class LazyFragment : Fragment() {

  private var isLoaded = false

  override fun onResume() {
    super.onResume()
    if (!isLoaded) {
      lazyInit()
      isLoaded = true
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    isLoaded = false
  }

  abstract fun lazyInit()
}