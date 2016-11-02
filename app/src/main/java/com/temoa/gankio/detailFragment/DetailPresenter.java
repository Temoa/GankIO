package com.temoa.gankio.detailFragment;

import android.content.Context;

import com.temoa.gankio.R;
import com.temoa.gankio.bean.NewGankData;

import java.util.List;

/**
 * Created by Temoa
 * on 2016/11/2 13:03
 */

class DetailPresenter implements DetailContract.Presenter, NetworkListener {

    private Context mContext;
    private DetailContract.View mDetailView;
    private DetailModel mDetailModel;

    DetailPresenter(Context context, DetailContract.View view) {
        mContext = context;
        mDetailView = view;
        mDetailView.setPresenter(this);
        mDetailModel = new DetailModel(context);
    }

    @Override
    public void start(String type) {
        if (mDetailModel != null) {
            mDetailModel.getData(type, DetailModel.FLAG_DATA_CACHE, this);
        }
    }

    @Override
    public void destroy() {
        if (mDetailView != null) {
            mDetailView = null;
        }

        if (mDetailModel != null) {
            mDetailModel.cancel();
            mDetailModel = null;
        }

        if (mContext != null) {
            mContext = null;
        }
    }

    @Override
    public void getNewData(String type) {
        if (mDetailModel != null) {
            mDetailModel.getData(type, DetailModel.FLAG_DATA_NEW, this);
        }
    }

    @Override
    public void getMoreData(String type, int page) {
        if (mDetailModel != null) {
            mDetailModel.getMoreData(type, page, this);
        }
    }

    @Override
    public void saveDataTODb(NewGankData.Results data) {
        if (mDetailModel != null) {
            boolean isSave = mDetailModel.addDataToDb(data);
            if (isSave) {
                if (mDetailView != null) {
                    mDetailView.showToast(mContext.getString(R.string.saved));
                }
            }
        }
    }

    @Override
    public void onSucceed(String type, List<NewGankData.Results> data) {
        if (mDetailView != null) {
            mDetailView.getData(type, data);
            mDetailView.hideProgressbar(false);
        }
    }

    @Override
    public void onError(String type, String error) {
        if (mDetailView != null) {
            mDetailView.showToast(error);
            mDetailView.hideProgressbar(false);
        }
    }

    @Override
    public void onLoadMoreSucceed(String type, List<NewGankData.Results> data) {
        if (mDetailView != null) {
            mDetailView.getMoreData(type, data);
        }
    }
}
