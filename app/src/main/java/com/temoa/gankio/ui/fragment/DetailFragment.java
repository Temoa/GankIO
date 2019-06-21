package com.temoa.gankio.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.kunminx.core.bus.IResponse;
import com.kunminx.core.bus.Result;
import com.temoa.gankio.R;
import com.temoa.gankio.bean.NewGankData;
import com.temoa.gankio.business.GankBus;
import com.temoa.gankio.business.GankConstant;
import com.temoa.gankio.tools.ToastUtils;
import com.temoa.gankio.ui.BaseFragment;
import com.temoa.gankio.ui.adapter.RecyclerAdapter;

import java.util.List;

/**
 * Created by Temoa
 * on 2016/8/1 16:46
 */
public class DetailFragment extends BaseFragment implements IResponse {

  private static final String BUNDLE_SAVE_KEY = "type";
  private Activity mContext;

  private RecyclerView mRecyclerView;
  private RecyclerAdapter mRecyclerAdapter;
  private SwipeRefreshLayout mRefreshLayout;

  private int pageIndex = 2;
  private String type;

  public DetailFragment getInstance(String type) {
    this.type = type;
    return this;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (rootView == null) {
      rootView = inflater.inflate(R.layout.fragment_main, container, false);
    }
    mRecyclerView = rootView.findViewById(R.id.fragment_recycler);
    mRefreshLayout = rootView.findViewById(R.id.fragment_swipeLayout);
    if (savedInstanceState != null && type == null) {
      type = savedInstanceState.getString(BUNDLE_SAVE_KEY);
    }
    mContext = getActivity();

    initRecycler();
    initSwipeLayout();

    GankBus.registerResponseObserver(this);
    if (type != null) GankBus.gank().getGank(type, GankConstant.FLAG_DATA_CACHE);

    return rootView;
  }

  @Override
  protected void onFragmentVisibleChange(boolean isVisible) {
    super.onFragmentVisibleChange(isVisible);
    if (isVisible) {
      if (type != null) GankBus.gank().getGank(type, GankConstant.FLAG_DATA_NEW);
    }
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    if (type != null)
      outState.putString(BUNDLE_SAVE_KEY, type);
  }

  private void initRecycler() {
    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    mRecyclerAdapter = new RecyclerAdapter(mContext, mRecyclerView, null);
    mRecyclerAdapter.addItemClickListener(new RecyclerAdapter.ItemClickListener() {
      @Override
      public void onItemClick(View v, NewGankData.Results data, int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(data.getUrl()));
        startActivity(intent);
      }
    });

    mRecyclerAdapter.addItemChildClickListener(new RecyclerAdapter.ItemChildClickListener() {
      @Override
      public void onItemChildClick(View v, NewGankData.Results data, int position) {
        saveFavDataToDB(data);
      }
    });

    mRecyclerAdapter.setLoadMord(true);
    mRecyclerAdapter.addLoadMoreListener(new RecyclerAdapter.LoadMoreListener() {
      @Override
      public void onLoadMore() {
        if (type != null) GankBus.gank().getMoreGank(type, pageIndex);
      }
    });

    mRecyclerView.setAdapter(mRecyclerAdapter);
  }

  private void initSwipeLayout() {
    mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        if (type != null) GankBus.gank().getGank(type, GankConstant.FLAG_DATA_NEW);
      }
    });
  }

  private void saveFavDataToDB(NewGankData.Results data) {
    if (type != null) GankBus.gank().saveGank2Db(type, data);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (mRecyclerView != null) mRecyclerView.setAdapter(null);
    GankBus.unregisterResponseObserver(this);
  }

  @Override
  public void onResult(Result result) {
    int resultCode = (int) result.getResultCode();
    if (resultCode == GankConstant.RESULT_CODE_ERROR + getTypeHashCode(type)) {
      ToastUtils.show(mContext, "请求失败");
    } else if (resultCode == GankConstant.RESULT_CODE_GET_NEW_DATA + getTypeHashCode(type)) {
      if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
        mRefreshLayout.setRefreshing(false);
      }
      if (mRecyclerAdapter != null) {
        List<NewGankData.Results> results = (List<NewGankData.Results>) result.getResultObject();
        mRecyclerAdapter.setNewData(results);
      }
    } else if (resultCode == GankConstant.RESULT_CODE_GET_MORE_DATA + getTypeHashCode(type)) {
      if (mRecyclerAdapter != null) {
        pageIndex++;
        List<NewGankData.Results> results = (List<NewGankData.Results>) result.getResultObject();
        mRecyclerAdapter.addData(results);
      }
    } else if (resultCode == GankConstant.RESULT_CODE_INSERT_FAV_DATA + getTypeHashCode(type)) {
      boolean insertSuccess = (boolean) result.getResultObject();
      ToastUtils.show(mContext, insertSuccess ? getString(R.string.saved) : "失败");
    }
  }

  private int getTypeHashCode(String type) {
    return type == null ? 0 : type.hashCode();
  }
}
