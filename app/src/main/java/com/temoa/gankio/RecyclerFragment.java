package com.temoa.gankio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.temoa.gankio.adapter.RecyclerAdapter;
import com.temoa.gankio.bean.GankData;
import com.temoa.gankio.callback.ICallback;
import com.temoa.gankio.db.DBHelper;
import com.temoa.gankio.db.DBTools;
import com.temoa.gankio.listener.RecyclerScrollListener;
import com.temoa.gankio.model.GankApi;
import com.temoa.gankio.tools.Divider;
import com.temoa.gankio.tools.LogUtils;
import com.temoa.gankio.tools.NetUtils;
import com.temoa.gankio.tools.ToastUtils;

import org.afinal.simplecache.ACache;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by Temoa
 * on 2016/8/1 16:46
 */
public class RecyclerFragment extends Fragment {

    private Activity context;
    private ACache mCache;
    private Call<GankData> mCall;

    private RecyclerView recycler;
    private RecyclerAdapter adapter;
    private SwipeRefreshLayout swipeLayout;

    private int pageIndex;
    private String type;

    public RecyclerFragment getInstance(String type) {
        this.type = type;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recycler = (RecyclerView) rootView.findViewById(R.id.fragment_rv);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragment_swipeLayout);
        context = getActivity();
        mCache = ACache.get(context);

        initRecycler();
        initSwipeLayout();
        getData();

        return rootView;
    }

    private ICallback<GankData> mCallback = new ICallback<GankData>() {
        @Override
        public void onSucceed(String type, String key, GankData gankData) {
            LogUtils.d("onSucceed");
            if (gankData.isError()) {
                ToastUtils.show(context, "数据加载出错,可能服务器抽风");
                return;
            }
            mCache.put(key, gankData, ACache.TIME_DAY * 2);
            LogUtils.d("cache key :" + key);
            ArrayList<GankData.Result> target = gankData.getResults();
            adapter.setData(target);
            swipeLayout.setRefreshing(false);
            pageIndex = 2;
        }

        @Override
        public void onError(String type, String key, String error) {
            LogUtils.e("onError" + error);
            getDataFromCache(key);
            swipeLayout.setRefreshing(false);
            pageIndex = 2;
        }
    };

    /**
     * 设置RecyclerView
     */
    private void initRecycler() {
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter = new RecyclerAdapter(context, new ArrayList<GankData.Result>(), R.layout.recycler_item);
        // 设置item的点击事件
        adapter.setItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, GankData.Result data, int position) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(data.getUrl()));
                startActivity(intent);
            }
        });
        // 设置item子view的点击事件
        adapter.setItemChildClickListener(new RecyclerAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v, GankData.Result data, int position) {
                saveFavDataToDB(data);
            }
        });
        recycler.setAdapter(adapter);
        // 设置Item的动画
        recycler.setItemAnimator(new DefaultItemAnimator());
        // 设置分割线
        Divider divider = new Divider(context, Divider.VERTICAL_LIST);
        recycler.addItemDecoration(divider);
        // RecyclerView的滑动监听器
        recycler.addOnScrollListener(new RecyclerScrollListener() {
            @Override
            public void onToolbarHide() {

            }

            @Override
            public void onToolbarShow() {

            }

            @Override
            public void loadMore() {
                // 上滑加载更多
                if (NetUtils.isNetConnection(context)) {
                    mCall = GankApi.getDataFromGank(type, 10, pageIndex, new ICallback<GankData>() {
                        @Override
                        public void onSucceed(String type, String key, GankData data) {
                            ArrayList<GankData.Result> target = data.getResults();
                            adapter.addData(target);
                            ++pageIndex;
                        }

                        @Override
                        public void onError(String type, String key, String error) {
                            ToastUtils.show(context, error);
                        }
                    });
                }
            }
        });
    }

    /**
     * 设置SwipeRefreshLayout
     */
    private void initSwipeLayout() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉更新
                String key = type + 10 + 1;
                if (NetUtils.isNetConnection(context)) {
                    mCall = GankApi.getDataFromGank(type, 10, 1, mCallback);
                } else {
                    ToastUtils.show(context, "网络异常");
                    swipeLayout.setRefreshing(false);
                    getDataFromCache(key);
                }
            }
        });
    }

    private void getData() {
        String key = type + 10 + 1;
        if (NetUtils.isNetConnection(context)) {
            mCall = GankApi.getDataFromGank(type, 10, 1, mCallback);
        } else {
            ToastUtils.show(context, "网络异常");
            getDataFromCache(key);
        }
    }

    private void getDataFromCache(String key) {
        LogUtils.e("get data key: " + key);
        if (mCache != null) {
            GankData data = (GankData) mCache.getAsObject(key);
            if (data != null) {
                ArrayList<GankData.Result> target = data.getResults();
                adapter.setData(target);
            }
        }
    }

    private void saveFavDataToDB(GankData.Result data) {
        DBHelper dbHelper = new DBHelper(context);
        DBTools.insert(dbHelper, data);
        ToastUtils.show(context, "已收藏");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (swipeLayout != null && swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
