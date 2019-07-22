package com.temoa.gankio.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickAction;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.temoa.gankio.Constants;
import com.temoa.gankio.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AboutActivity extends MaterialAboutActivity {

  private MaterialAboutActionItem mGankUpdateItem;

  @NonNull
  @Override
  protected MaterialAboutList getMaterialAboutList(@NonNull Context context) {
    MaterialAboutCard.Builder builder1 = new MaterialAboutCard.Builder();
    builder1.addItem(new MaterialAboutTitleItem.Builder()
        .icon(R.mipmap.ic_launcher)
        .desc(R.string.gank_io_introduce)
        .text(R.string.about_gank_io)
        .build());
    builder1.addItem(new MaterialAboutActionItem.Builder()
        .icon(R.drawable.ic_about_black_24)
        .text("应用版本")
        .subText(getAppVersionName())
        .build());
    builder1.addItem(mGankUpdateItem = new MaterialAboutActionItem.Builder()
        .icon(R.drawable.ic_about_black_24)
        .text("历史的车轮滚滚向前")
        .subText("")
        .build());
    getGankNewDataDate();

    MaterialAboutCard.Builder builder2 = new MaterialAboutCard.Builder();
    builder2.addItem(new MaterialAboutTitleItem.Builder()
        .icon(R.drawable.me)
        .text(R.string.my_name)
        .desc(R.string.my_crafted)
        .build());
    builder2.addItem(new MaterialAboutActionItem.Builder()
        .icon(R.drawable.github)
        .text("Github")
        .subText(Constants.URL_Github)
        .setOnClickAction(new MaterialAboutItemOnClickAction() {
          @Override
          public void onClick() {
            openUrl(Constants.URL_Github);
          }
        })
        .build());

    MaterialAboutCard.Builder builder3 = new MaterialAboutCard.Builder();
    builder3.title("离不开开源的帮助");
    getOpenSourceItems(setOpenSources(), builder3);

    MaterialAboutCard.Builder builder4 = new MaterialAboutCard.Builder();
    builder4.title("标题图源");
    builder4.addItem(new MaterialAboutActionItem.Builder()
        .icon(R.drawable.unsplash)
        .text("Android")
        .subText("Photo by Dmitry Bayer on Unsplash")
        .build());
    builder4.addItem(new MaterialAboutActionItem.Builder()
        .icon(R.drawable.unsplash)
        .text("iOS")
        .subText("Photo by Tyler Lastovich on Unsplash")
        .build());
    builder4.addItem(new MaterialAboutActionItem.Builder()
        .icon(R.drawable.unsplash)
        .text("Web")
        .subText("Photo by Sai Kiran Anagani on Unsplash")
        .build());

    return
        new MaterialAboutList.Builder()
            .addCard(builder1.build())
            .addCard(builder2.build())
            .addCard(builder3.build())
            .addCard(builder4.build())
            .build();
  }

  @Nullable
  @Override
  protected CharSequence getActivityTitle() {
    return getString(R.string.about);
  }

  private String getAppVersionName() {
    String versionName;
    try {
      PackageManager pm = getPackageManager();
      PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
      versionName = pi.versionName;
      if (versionName == null) return "";
    } catch (Exception e) {
      return "";
    }
    return versionName;
  }

  private void getGankNewDataDate() {
    OkHttpClient okHttpClient = new OkHttpClient();
    Request request = new Request.Builder().url("http://gank.io/history").build();
    okHttpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        // to do nothings
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful() && response.body() != null) {
          matches(response.body().string());
        } else {
          onFailure(call, null);
        }
      }
    });
  }

  private void matches(String source) {
    Pattern r = Pattern.compile("<span class=\"u-pull-right\">(.*)</span>");
    Matcher m = r.matcher(source);
    if (m.find()) {
      mGankUpdateItem.setSubText(m.group(1));
      refreshMaterialAboutList();
    }
  }

  private List<OpenSource> setOpenSources() {
    List<OpenSource> openSources = new ArrayList<>();
    openSources.add(new OpenSource("glide", "https://github.com/bumptech/glide"));
    openSources.add(new OpenSource("retrofit", "https://square.github.io/retrofit"));
    openSources.add(new OpenSource("RxJava", "https://github.com/ReactiveX/RxJava"));
    openSources.add(new OpenSource("RxAndroid", "https://github.com/ReactiveX/RxAndroid"));
    openSources.add(new OpenSource("MaterialViewPager", "https://github.com/florent37/MaterialViewPager"));
    openSources.add(new OpenSource("material-about-library", "https://github.com/daniel-stoneuk/material-about-library"));
    return openSources;
  }

  private void getOpenSourceItems(List<OpenSource> list, MaterialAboutCard.Builder builder) {
    for (final OpenSource openSource : list) {
      builder.addItem(new MaterialAboutActionItem.Builder()
          .icon(R.drawable.ic_about_black_24)
          .text(openSource.name)
          .subText(openSource.url)
          .setOnClickAction(new MaterialAboutItemOnClickAction() {
            @Override
            public void onClick() {
              openUrl(openSource.url);
            }
          })
          .build());
    }
  }

  static class OpenSource {
    final String name;
    final String url;

    OpenSource(String name, String url) {
      this.name = name;
      this.url = url;
    }
  }

  private void openUrl(String uri) {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(uri));
    startActivity(intent);
  }
}
