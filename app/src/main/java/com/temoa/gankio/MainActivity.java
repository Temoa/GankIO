package com.temoa.gankio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.temoa.gankio.adapter.ViewPagerAdapter;
import com.temoa.gankio.bean.NewGankData;
import com.temoa.gankio.network.BuildService;

import org.afinal.simplecache.ACache;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private static final String[] TITLE_LIST = {Constants.TYPE_ANDROID, Constants.TYPE_IOS, Constants.TYPE_WEB};
    private String[] beauty = new String[3];
    private String mTargetUrl = Constants.Default_PIC;
    private ACache mCache;

    private MaterialViewPager mMaterialViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareData();
        initViews();
        getBeauty();
    }

    private void prepareData() {
        mCache = ACache.get(this);
        NewGankData data = (NewGankData) mCache.getAsObject(Constants.TYPE_BEAUTY);
        if (data != null) {
            for (int i = 0; i < 3; i++) {
                beauty[i] = data.getResults().get(i).getUrl();
            }
        } else {
            for (int i = 0; i < 3; i++) {
                beauty[i] = Constants.Default_PIC;
            }
        }

        mTargetUrl = beauty[0];
    }

    private void initViews() {
        mMaterialViewPager = (MaterialViewPager) findViewById(R.id.main_material_pager);
        Toolbar toolbar = mMaterialViewPager.getToolbar();
        if (toolbar != null) {
            toolbar.setTitle("");
            toolbar.setPopupTheme(R.style.AppBaseTheme_PopupOverlay);
            toolbar.setNavigationIcon(R.drawable.ic_nav);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mMaterialViewPager.getViewPager()
                .setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), TITLE_LIST));
        mMaterialViewPager.setMaterialViewPagerListener(
                new MaterialViewPager.Listener() {
                    @Override
                    public HeaderDesign getHeaderDesign(int page) {
                        mTargetUrl = beauty[page];
                        return HeaderDesign.fromColorResAndUrl(R.color.colorPrimary, mTargetUrl);
                    }
                });
        mMaterialViewPager.getViewPager()
                .setOffscreenPageLimit(mMaterialViewPager.getViewPager().getAdapter().getCount());
        mMaterialViewPager.getPagerTitleStrip().setViewPager(mMaterialViewPager.getViewPager());

        TextView headerText = (TextView) findViewById(R.id.main_logo);
        headerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoActivity.launch(MainActivity.this, mTargetUrl);
            }
        });
    }

    private void getBeauty() {
        BuildService.getGankService().getBeauty()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewGankData>() {
                    @Override
                    public void call(NewGankData data) {
                        if (!data.isError()) {
                            for (int i = 0; i < 3; i++) {
                                beauty[i] = data.getResults().get(i).getUrl();
                            }

                            if (mCache != null)
                                mCache.put(Constants.TYPE_BEAUTY, data);
                        }
                        mMaterialViewPager.onPageSelected(0);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCache != null)
            mCache = null;
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
}
