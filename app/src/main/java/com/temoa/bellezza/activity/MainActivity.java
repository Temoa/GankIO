package com.temoa.bellezza.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.temoa.bellezza.R;
import com.temoa.bellezza.adapter.RecyclerAdapter;
import com.temoa.bellezza.adapter.RollViewPagerAdapter;
import com.temoa.bellezza.listener.RecyclerScrollListener;
import com.temoa.bellezza.presenter.MainViewPresenter;
import com.temoa.bellezza.view.IMainView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MainViewPresenter mainViewPresenter;
    private RecyclerAdapter recyclerAdapter;
    private RollViewPagerAdapter rollViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        mainViewPresenter = new MainViewPresenter(this);
        mainViewPresenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        mainViewPresenter.onDestroy();
        super.onDestroy();
    }

    //初始化界面
    private void initView() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.write));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_green_light);
        //调整进度球距离顶部的距离，解决toolbar遮挡进度球的问题。并且为了第一次进入页面的时候显示加载进度球
        swipeRefreshLayout.setProgressViewOffset(false,24,
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,64,getResources().getDisplayMetrics()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerScrollListener() {
            @Override
            public void onHide() {
                toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
            }

            @Override
            public void onShow() {
                toolbar.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2));
            }

            @Override
            public void loadMore() {
                mainViewPresenter.addMoreItem();
            }
        });
    }

    //swipeRefreshLayout下拉刷新监听
    @Override
    public void onRefresh() {
        mainViewPresenter.loadItem();
        mainViewPresenter.loadPhotoUrl();
    }

    //显示隐藏ProgressBar
    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    //加载图片数据，初始化rollPagerViewAdapter
    @Override
    public void getPhoto(List<String> photoUrlList) {
        rollViewPagerAdapter = new RollViewPagerAdapter(photoUrlList);
    }

    //加载item数据，初始化RecyclerView的Adapter，并且绑定Adapter
    @Override
    public void getItem(List<String> titleList) {
        recyclerAdapter = new RecyclerAdapter(titleList, rollViewPagerAdapter);
        recyclerAdapter.setOnRecyclerViewItemClikcListener(new RecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mainViewPresenter.onItemClick(position);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
    }

    //上拉加载更多数据
    @Override
    public void loadMoreItem() {
        recyclerAdapter.notifyDataSetChanged();
    }

    //吐司
    @Override
    public void showToast(String str) {
        final Snackbar snackbar = Snackbar.make(swipeRefreshLayout, str, Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.setAction("刷新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    //跳转到WebView显示
    @Override
    public void toWebActivity(String url) {
        Intent intent = new Intent();
        intent.setClass(this, WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
