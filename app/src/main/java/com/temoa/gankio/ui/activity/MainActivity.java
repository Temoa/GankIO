package com.temoa.gankio.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.temoa.gankio.Constants;
import com.temoa.gankio.R;
import com.temoa.gankio.business.GankBus;
import com.temoa.gankio.business.GankBusiness;
import com.temoa.gankio.tools.ToastUtils;
import com.temoa.gankio.ui.adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

  private static final String[] TITLE_LIST = {Constants.TYPE_ANDROID, Constants.TYPE_IOS, Constants.TYPE_WEB};
  private GankBusiness mGankBusiness;

  private Drawable mAndroidDrawable;
  private Drawable miOSDrawable;
  private Drawable mWebDrawable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initViews();
    mGankBusiness = new GankBusiness();
    mGankBusiness.init(this.getApplicationContext());
    GankBus.registerRequestHandler(mGankBusiness);
  }

  private void initViews() {
    MaterialViewPager materialViewPager = findViewById(R.id.main_material_pager);

    Toolbar toolbar = materialViewPager.getToolbar();
    if (toolbar != null) {
      toolbar.setTitle("");
      toolbar.setPopupTheme(R.style.AppBaseTheme_PopupOverlay);
      toolbar.setNavigationIcon(R.drawable.ic_nav);
      setSupportActionBar(toolbar);
      if (getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), TITLE_LIST);
    materialViewPager.getViewPager().setAdapter(viewPagerAdapter);


    MaterialViewPager.Listener materialPagerListener = new MaterialViewPager.Listener() {
      @Override
      public HeaderDesign getHeaderDesign(int page) {
        getHeaderPicture();
        switch (page) {
          default:
          case 0:
            return HeaderDesign.fromColorAndDrawable(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary), mAndroidDrawable);
          case 1:
            return HeaderDesign.fromColorAndDrawable(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary), miOSDrawable);
          case 2:
            return HeaderDesign.fromColorAndDrawable(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary), mWebDrawable);
        }
      }
    };
    materialViewPager.setMaterialViewPagerListener(materialPagerListener);

    ViewPager pager = materialViewPager.getViewPager();
    materialViewPager.getViewPager().setOffscreenPageLimit(3);
    materialViewPager.getPagerTitleStrip().setViewPager(pager);
  }

  private void getHeaderPicture() {
    if (mAndroidDrawable == null)
      mAndroidDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.pic_main_header_android);

    if (miOSDrawable == null)
      miOSDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.pic_main_header_ios);

    if (mWebDrawable == null)
      mWebDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.pic_mian_header_web);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    GankBus.unregisterRequestHandler(mGankBusiness);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.menu_collect:
        startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
        break;
      case R.id.menu_about:
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private long exitTime = 0;

  @Override
  public void onBackPressed() {
    if ((System.currentTimeMillis() - exitTime) > 2000) {
      ToastUtils.show(this, "再次点击一次退出应用");
      exitTime = System.currentTimeMillis();
    } else {
      finish();
    }
  }
}
