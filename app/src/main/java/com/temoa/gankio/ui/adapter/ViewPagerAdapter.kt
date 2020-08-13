package com.temoa.gankio.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.temoa.gankio.Constants
import com.temoa.gankio.ui.fragment.DetailFragment

/**
 * Created by Temoa
 * on 2016/8/1 18:44
 */
class ViewPagerAdapter(fm: FragmentManager, private val titleList: Array<String>)
  : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getItem(position: Int): Fragment {
    val fragment = DetailFragment()
    fragment.arguments = Bundle().apply {
      putString(Constants.TYPE, titleList[position])
    }
    return fragment
  }

  override fun getCount(): Int {
    return titleList.size
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return titleList[position]
  }
}