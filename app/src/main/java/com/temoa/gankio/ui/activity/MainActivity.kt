package com.temoa.gankio.ui.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.florent37.materialviewpager.MaterialViewPager
import com.github.florent37.materialviewpager.header.HeaderDesign
import com.temoa.gankio.Constants
import com.temoa.gankio.R
import com.temoa.gankio.tools.ToastUtils.show
import com.temoa.gankio.ui.adapter.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

  private var mAndroidDrawable: Drawable? = null
  private var miOSDrawable: Drawable? = null
  private var mWebDrawable: Drawable? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initViews()
  }

  private fun initViews() {
    val materialViewPager = findViewById<MaterialViewPager>(R.id.main_material_pager)
    val toolbar = materialViewPager.toolbar
    if (toolbar != null) {
      toolbar.title = ""
      toolbar.popupTheme = R.style.AppBaseTheme_PopupOverlay
      toolbar.setNavigationIcon(R.drawable.ic_nav)
      setSupportActionBar(toolbar)
      if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
    val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, TITLE_LIST)
    materialViewPager.viewPager.adapter = viewPagerAdapter
    val materialPagerListener = MaterialViewPager.Listener { page ->
      headerPicture
      when (page) {
        0 -> HeaderDesign.fromColorAndDrawable(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary), mAndroidDrawable)
        1 -> HeaderDesign.fromColorAndDrawable(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary), miOSDrawable)
        2 -> HeaderDesign.fromColorAndDrawable(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary), mWebDrawable)
        else -> HeaderDesign.fromColorAndDrawable(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary), mAndroidDrawable)
      }
    }
    materialViewPager.setMaterialViewPagerListener(materialPagerListener)
    val pager = materialViewPager.viewPager
    materialViewPager.viewPager.offscreenPageLimit = 3
    materialViewPager.pagerTitleStrip.setViewPager(pager)
  }

  private val headerPicture: Unit
    get() {
      if (mAndroidDrawable == null) mAndroidDrawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.pic_main_header_android)
      if (miOSDrawable == null) miOSDrawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.pic_main_header_ios)
      if (mWebDrawable == null) mWebDrawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.pic_mian_header_web)
    }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_collect -> startActivity(Intent(this@MainActivity, FavoritesActivity::class.java))
      R.id.menu_about -> startActivity(Intent(this@MainActivity, AboutActivity::class.java))
    }
    return super.onOptionsItemSelected(item)
  }

  private var exitTime: Long = 0
  override fun onBackPressed() {
    if (System.currentTimeMillis() - exitTime > 2000) {
      show(this, "再次点击一次退出应用")
      exitTime = System.currentTimeMillis()
    } else {
      finish()
    }
  }

  companion object {
    private val TITLE_LIST = arrayOf(Constants.TYPE_ANDROID, Constants.TYPE_IOS, Constants.TYPE_WEB)
  }
}