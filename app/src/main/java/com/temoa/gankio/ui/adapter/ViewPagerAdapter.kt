package com.temoa.gankio.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.temoa.gankio.ui.fragment.DetailFragment

/**
 * Created by Temoa
 * on 2016/8/1 18:44
 */
class ViewPagerAdapter(fm: FragmentManager?, private val titleList: Array<String>?) : FragmentStatePagerAdapter(fm!!) {

  override fun getItem(position: Int): Fragment {
    return DetailFragment().getInstance(titleList!![position])
  }

  override fun getCount(): Int {
    return titleList?.size ?: 0
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return titleList?.get(position)
  }
}