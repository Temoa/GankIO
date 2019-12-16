package com.temoa.gankio.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.temoa.gankio.R
import com.temoa.gankio.bean.NewGankData.Results
import com.temoa.gankio.db.DBHelper
import com.temoa.gankio.db.DBTools.deleteByDesc
import com.temoa.gankio.db.DBTools.queryAll
import com.temoa.gankio.ui.adapter.RecyclerAdapter
import com.temoa.gankio.ui.adapter.RecyclerAdapter.ItemClickListener
import com.temoa.gankio.ui.adapter.RecyclerAdapter.ItemLongClickListener

class FavoritesActivity : AppCompatActivity() {

  private var adapter: RecyclerAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_favorites)
    val toolbar = findViewById<View>(R.id.fav_activity_toolbar) as Toolbar
    toolbar.setTitle(R.string.collect)
    setSupportActionBar(toolbar)
    if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { onBackPressed() }
    initRecycler()
    dataFromDB
  }

  private fun initRecycler() {
    val recycler = findViewById<View>(R.id.fav_activity_rv) as RecyclerView
    recycler.setHasFixedSize(true)
    recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    recycler.itemAnimator = DefaultItemAnimator()
    adapter = RecyclerAdapter(this, recycler, null)
    adapter!!.isTypeReplaceAuthor(true)
    adapter!!.addItemClickListener(object : ItemClickListener {
      override fun onItemClick(v: View?, data: Results, position: Int) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        intent.data = Uri.parse(data.url)
        startActivity(intent)
      }
    })
    adapter!!.addItemLongClickListener(object : ItemLongClickListener {
      override fun onItemLongClick(v: View?, data: Results, position: Int) {
        createPopupMenu(v, data.desc)
      }
    })
    recycler.adapter = adapter
  }

  private val dataFromDB: Unit
    get() {
      val dbHelper = DBHelper(this)
      val dataList = queryAll(dbHelper)
      adapter!!.setNewData(dataList)
    }

  private fun deleteDataInDB(desc: String?) {
    val dbHelper = DBHelper(this)
    deleteByDesc(dbHelper, desc!!)
  }

  private fun createPopupMenu(v: View?, desc: String?) {
    val menu = PopupMenu(this, v!!)
    menu.menuInflater.inflate(R.menu.fav_menu, menu.menu)
    menu.setOnMenuItemClickListener {
      deleteDataInDB(desc)
      menu.dismiss()
      dataFromDB
      true
    }
    menu.show()
  }
}