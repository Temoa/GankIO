package com.temoa.gankio.mvpFragment;

import android.content.Context;

import com.temoa.gankio.R;
import com.temoa.gankio.bean.NewGankData.Results;

import java.util.List;

/**
 * Created by Temoa
 * on 2016/10/20 17:32
 */

public class Presenter implements NetworkListener {

    private Context mContext;
    private IView mView;
    private IModel mModel;

    public Presenter(Context context, IView iView) {
        mContext = context;
        mView = iView;
        mModel = new ModelImpl(context);
    }

    public void onCreate(String type) {
        if (mModel != null)
            mModel.getData(type, ModelImpl.FLAG_DATA_CACHE, this);
    }

    public void onDestroy() {
        if (mView != null)
            mView = null;
        if (mModel != null) {
            mModel.cancel();
            mModel = null;
        }
        if (mContext != null) {
            mContext = null;
        }
    }

    public void getNewData(String type) {
        if (mModel != null)
            mModel.getData(type, ModelImpl.FLAG_DATA_NEW, this);
    }

    public void getMoreData(String type, int page) {
        if (mModel != null)
            mModel.getMoreData(type, page, this);
    }

    public void saveDataTODb(Results data) {
        if (mModel != null) {
            boolean isSaved = mModel.addDataToDb(data);
            if (isSaved)
                if (mView != null)
                    mView.showToast(mContext.getResources().getString(R.string.saved));
        }
    }

    @Override
    public void onSucceed(String type, List<Results> data) {
        if (mView != null) {
            mView.getData(type, data);
            mView.hideProgressbar(false);
        }
    }

    @Override
    public void onError(String type, String error) {
        if (mView != null) {
            mView.showToast(error);
            mView.hideProgressbar(false);
        }
    }

    @Override
    public void onLoadMoreSucceed(String type, List<Results> data) {
        if (mView != null)
            mView.getMoreData(type, data);
    }
}
