package com.temoa.bellezza.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.temoa.bellezza.R;
import com.temoa.bellezza.adapter.CustomAdapter;
import com.temoa.bellezza.presenter.MainViewPresenter;
import com.temoa.bellezza.view.IMainView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements IMainView,
        AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MainViewPresenter mainViewPresenter;
    private CustomAdapter mAdapter;
    //生命周期：onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.write));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_green_light);
        mainViewPresenter = new MainViewPresenter(this);
        mainViewPresenter.onCreate();
    }
    //生命周期：onDestroy
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        mainViewPresenter.onDestroy();
        super.onDestroy();
    }
    //swipeRefreshLayout下拉刷新监听
    @Override
    public void onRefresh() {
        mainViewPresenter.loadItem();
    }
    //ListView子项点击事件监听
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mainViewPresenter.onItemClick(position);
    }
    //ListView滑动事件监听
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastItem = firstVisibleItem + visibleItemCount;
        if (lastItem == totalItemCount) {
            View lastItemView = listView.getChildAt(listView.getChildCount() - 1);
            if ((listView.getBottom()) == (lastItemView.getBottom())) {
                mainViewPresenter.addMoreItem();
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

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
    //加载和下拉刷新数据
    @Override
    public void getItem(List<String> items) {
        mAdapter = new CustomAdapter(this, items);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
    }
    //上拉加载更多数据
    @Override
    public void loadMoreItem() {
        mAdapter.notifyDataSetChanged();
    }
    //吐司
    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
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
