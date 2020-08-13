package com.temoa.gankio.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import com.temoa.gankio.R
import com.temoa.gankio.bean.GankData
import com.temoa.gankio.databinding.ActivityFavoritesBinding
import com.temoa.gankio.ui.adapter.GankDataAdapter
import com.temoa.gankio.viewmodel.FavViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesActivity : AppCompatActivity() {

  private val mFavViewModel: FavViewModel by viewModel()

  private lateinit var mGankDataAdapter: GankDataAdapter

  private lateinit var mViewBinding: ActivityFavoritesBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mViewBinding = ActivityFavoritesBinding.inflate(layoutInflater)
    setContentView(mViewBinding.root)

    mViewBinding.apply {
      favToolbar.setTitle(R.string.collect)
      setSupportActionBar(favToolbar)
      if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      favToolbar.setNavigationOnClickListener { onBackPressed() }

      favRecycler.setHasFixedSize(true)
      mGankDataAdapter = GankDataAdapter(
          onItemClick = { _, data, _ ->
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = Uri.parse(data.url)
            startActivity(intent)
          },
          onItemLongClick = { view, data, _ ->
            createPopupMenu(view, data)
            true
          }
      ).apply {
        isTypeReplaceAuthor(true)
      }
      favRecycler.adapter = mGankDataAdapter
    }

    mFavViewModel.favLiveData.observe(this, Observer {
      mGankDataAdapter.submitData(lifecycle, it)
    })
  }

  private fun createPopupMenu(v: View, gankData: GankData) {
    val menu = PopupMenu(this, v)
    menu.menuInflater.inflate(R.menu.fav_menu, menu.menu)
    menu.setOnMenuItemClickListener {
      mFavViewModel.delete(gankData)
      true
    }
    menu.show()
  }
}