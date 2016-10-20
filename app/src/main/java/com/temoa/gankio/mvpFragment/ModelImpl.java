package com.temoa.gankio.mvpFragment;

import android.content.Context;
import android.util.Log;

import com.temoa.gankio.bean.NewGankData;
import com.temoa.gankio.db.DBHelper;
import com.temoa.gankio.db.DBTools;
import com.temoa.gankio.network.BuildService;

import org.afinal.simplecache.ACache;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.temoa.gankio.network.BuildService.getGankService;

/**
 * Created by Temoa
 * on 2016/10/20 17:32
 */

public class ModelImpl implements IModel {

    private static final String TAG = "ModelImpl";
    public static final int FLAG_DATA_CACHE = 0;
    public static final int FLAG_DATA_NEW = 1;

    private Context mContext;
    private ACache mCache;
    private Observable<NewGankData> mGetNewData;
    private Observable<NewGankData> mGetMoreData;

    public ModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public void getData(final String type, int flag, final NetworkListener listener) {
        if (flag == FLAG_DATA_CACHE) {
            mCache = ACache.get(mContext);
            NewGankData data = (NewGankData) mCache.getAsObject(type);
            if (data != null) {
                listener.onSucceed(type, data.getResults());
                return;
            }
        }
        mGetNewData = BuildService.getGankService().getNewData(type, 10, 1);
        mGetNewData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewGankData>() {
                    @Override
                    public void call(NewGankData data) {
                        if (!data.isError()) {
                            if (mCache != null)
                                mCache.put(type, data);

                            listener.onSucceed(type, data.getResults());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, throwable.toString());
                        listener.onError(type, throwable.getMessage());
                    }
                });
    }

    @Override
    public void getMoreData(final String type, int page, final NetworkListener listener) {
        mGetMoreData = getGankService().getNewData(type, 10, page);
        mGetMoreData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewGankData>() {
                    @Override
                    public void call(NewGankData data) {
                        if (!data.isError())
                            listener.onLoadMoreSucceed(type, data.getResults());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, throwable.toString());
                        listener.onError(type, throwable.getMessage());
                    }
                });
    }

    @Override
    public boolean addDataToDb(NewGankData.Results data) {
        DBHelper dbHelper = new DBHelper(mContext);
        return DBTools.insert(dbHelper, data);
    }

    @Override
    public void cancel() {
        if (mGetMoreData != null)
            mGetMoreData.unsubscribeOn(AndroidSchedulers.mainThread());
        if (mGetNewData != null)
            mGetNewData.unsubscribeOn(AndroidSchedulers.mainThread());
        if (mCache != null)
            mCache = null;
    }
}
