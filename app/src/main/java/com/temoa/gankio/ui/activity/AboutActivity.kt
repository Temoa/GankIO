package com.temoa.gankio.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.temoa.gankio.Constants
import com.temoa.gankio.R
import okhttp3.*
import java.io.IOException
import java.util.*
import java.util.regex.Pattern

class AboutActivity : MaterialAboutActivity() {

  private var mGankUpdateItem: MaterialAboutActionItem? = null

  override fun getMaterialAboutList(context: Context): MaterialAboutList {
    val builder1 = MaterialAboutCard.Builder()
    builder1.addItem(MaterialAboutTitleItem.Builder()
        .icon(R.mipmap.ic_launcher)
        .desc(R.string.gank_io_introduce)
        .text(R.string.about_gank_io)
        .build())
    builder1.addItem(MaterialAboutActionItem.Builder()
        .icon(R.drawable.ic_about_black_24)
        .text("应用版本")
        .subText(appVersionName)
        .build())
    builder1.addItem(MaterialAboutActionItem.Builder()
        .icon(R.drawable.ic_about_black_24)
        .text("历史的车轮滚滚向前")
        .subText("")
        .build().also { mGankUpdateItem = it })
    gankNewDataDate
    val builder2 = MaterialAboutCard.Builder()
    builder2.addItem(MaterialAboutTitleItem.Builder()
        .icon(R.drawable.me)
        .text(R.string.my_name)
        .desc(R.string.my_crafted)
        .build())
    builder2.addItem(MaterialAboutActionItem.Builder()
        .icon(R.drawable.github)
        .text("Github")
        .subText(Constants.URL_Github)
        .setOnClickAction { openUrl(Constants.URL_Github) }
        .build())
    val builder3 = MaterialAboutCard.Builder()
    builder3.title("离不开开源的帮助")
    getOpenSourceItems(setOpenSources(), builder3)
    val builder4 = MaterialAboutCard.Builder()
    builder4.title("标题图源")
    builder4.addItem(MaterialAboutActionItem.Builder()
        .icon(R.drawable.unsplash)
        .text("Android")
        .subText("Photo by Dmitry Bayer on Unsplash")
        .build())
    builder4.addItem(MaterialAboutActionItem.Builder()
        .icon(R.drawable.unsplash)
        .text("iOS")
        .subText("Photo by Tyler Lastovich on Unsplash")
        .build())
    builder4.addItem(MaterialAboutActionItem.Builder()
        .icon(R.drawable.unsplash)
        .text("Web")
        .subText("Photo by Sai Kiran Anagani on Unsplash")
        .build())
    return MaterialAboutList.Builder()
        .addCard(builder1.build())
        .addCard(builder2.build())
        .addCard(builder3.build())
        .addCard(builder4.build())
        .build()
  }

  override fun getActivityTitle(): CharSequence? {
    return getString(R.string.about)
  }

  private val appVersionName: String
    get() {
      val versionName: String
      try {
        val pm = packageManager
        val pi = pm.getPackageInfo(packageName, 0)
        versionName = pi.versionName
        if (versionName == null) return ""
      } catch (e: Exception) {
        return ""
      }
      return versionName
    }

  private val gankNewDataDate: Unit
    get() {
      val okHttpClient = OkHttpClient()
      val request = Request.Builder().url("http://gank.io/history").build()
      okHttpClient.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) { // to do nothings
        }

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
          if (response.isSuccessful && response.body() != null) {
            matches(response.body()!!.string())
          } else {
            onFailure(call, IOException("Response is not Successful or response body is null"))
          }
        }
      })
    }

  private fun matches(source: String) {
    val r = Pattern.compile("<span class=\"u-pull-right\">(.*)</span>")
    val m = r.matcher(source)
    if (m.find()) {
      mGankUpdateItem!!.subText = m.group(1)
      refreshMaterialAboutList()
    }
  }

  private fun setOpenSources(): List<OpenSource> {
    val openSources: MutableList<OpenSource> = ArrayList()
    openSources.add(OpenSource("glide", "https://github.com/bumptech/glide"))
    openSources.add(OpenSource("retrofit", "https://square.github.io/retrofit"))
    openSources.add(OpenSource("RxJava", "https://github.com/ReactiveX/RxJava"))
    openSources.add(OpenSource("RxAndroid", "https://github.com/ReactiveX/RxAndroid"))
    openSources.add(OpenSource("MaterialViewPager", "https://github.com/florent37/MaterialViewPager"))
    openSources.add(OpenSource("material-about-library", "https://github.com/daniel-stoneuk/material-about-library"))
    return openSources
  }

  private fun getOpenSourceItems(list: List<OpenSource>, builder: MaterialAboutCard.Builder) {
    for (openSource in list) {
      builder.addItem(MaterialAboutActionItem.Builder()
          .icon(R.drawable.ic_about_black_24)
          .text(openSource.name)
          .subText(openSource.url)
          .setOnClickAction { openUrl(openSource.url) }
          .build())
    }
  }

  internal class OpenSource(val name: String, val url: String)

  private fun openUrl(uri: String) {
    val intent = Intent()
    intent.action = Intent.ACTION_VIEW
    intent.data = Uri.parse(uri)
    startActivity(intent)
  }
}