package com.temoa.gankio.detailFragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.temoa.gankio.BaseFragment;
import com.temoa.gankio.R;
import com.temoa.gankio.adapter.RecyclerAdapter;
import com.temoa.gankio.bean.NewGankData;
import com.temoa.gankio.tools.ToastUtils;

import java.util.List;

/**
 * Created by Temoa
 * on 2016/8/1 16:46
 */
public class DetailFragment extends BaseFragment implements DetailContract.View {

    private static final String BUNDLE_SAVE_KEY = "type";
    private Activity mContext;
    private DetailContract.Presenter mPresenter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_recycler);
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragment_swipeLayout);

        if (savedInstanceState != null && type == null)
            type = savedInstanceState.getString(BUNDLE_SAVE_KEY);

        mContext = getActivity();
        mPresenter = new DetailPresenter(mContext, this);

        initRecycler();
        initSwipeLayout();

        if (type != null) {
            mPresenter.start(type);
        }

        return rootView;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (mPresenter != null && type != null)
                mPresenter.getNewData(type);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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
                if (mPresenter != null)
                    mPresenter.getMoreData(type, pageIndex);
            }
        });

        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void initSwipeLayout() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPresenter != null)
                    mPresenter.getNewData(type);
            }
        });
    }


    private void saveFavDataToDB(NewGankData.Results data) {
        if (mPresenter != null)
            mPresenter.saveDataTODb(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getData(String type, List<NewGankData.Results> data) {
        if (mRecyclerAdapter != null)
            mRecyclerAdapter.setNewData(data);
    }

    @Override
    public void getMoreData(String type, List<NewGankData.Results> data) {
        pageIndex++;
        if (mRecyclerAdapter != null)
            mRecyclerAdapter.addData(data);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.show(mContext, msg);
    }

    @Override
    public void hideProgressbar(boolean state) {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing())
            mRefreshLayout.setRefreshing(state);
    }
}
