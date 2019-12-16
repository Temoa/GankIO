package com.temoa.gankio.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.temoa.gankio.R

class PhotoActivity : AppCompatActivity() {

  private var url: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_photo)
    val intent = intent
    if (intent != null) url = intent.getStringExtra(EXTRA_URL)
    initToolbar()
    initPhoto()
  }

  private fun initToolbar() {
    val toolbar = findViewById<View>(R.id.photo_toolbar) as Toolbar
    toolbar.title = ""
    setSupportActionBar(toolbar)
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
    toolbar.setNavigationOnClickListener { onBackPressed() }
  }

  private fun initPhoto() {
    val progressBar = findViewById<View>(R.id.photo_progressBar) as ProgressBar
    progressBar.visibility = View.VISIBLE
    val img = findViewById<View>(R.id.photo_img) as ImageView
    Glide.with(this)
        .load(url)
        .dontAnimate()
        .fitCenter()
        .into(object : GlideDrawableImageViewTarget(img) {
          override fun onResourceReady(resource: GlideDrawable,
                                       animation: GlideAnimation<in GlideDrawable>) {
            super.onResourceReady(resource, animation)
            progressBar.visibility = View.GONE
          }
        })
  }

  companion object {
    private const val EXTRA_URL = "url"
    fun launch(activity: Activity, url: String?) {
      val intent = Intent(activity, PhotoActivity::class.java)
      intent.putExtra(EXTRA_URL, url)
      activity.startActivity(intent)
    }
  }
}