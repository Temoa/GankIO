package com.temoa.gankio.business;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.temoa.gankio.bean.NewGankData;
import com.temoa.gankio.db.DBHelper;
import com.temoa.gankio.db.DBTools;
import com.temoa.gankio.network.BuildService;
import com.temoa.gankio.tools.LogUtils;

import org.afinal.simplecache.ACache;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.temoa.gankio.network.BuildService.getGankService;

/**
 * Created by lai
 * on 2019/7/22.
 */
public class GankViewModel extends AndroidViewModel {

  private static final String TAG = "GankViewModel";

  private Application mApplication;
  private ACache mCache;
  private CompositeDisposable mCompositeDisposable;

  private MutableLiveData<List<NewGankData.Results>> mGankData;
  private MutableLiveData<List<NewGankData.Results>> mMoreGankData;
  private MutableLiveData<Boolean> mSaveDataResult;

  public GankViewModel(@NonNull Application application) {
    super(application);
    mApplication = application;
    mCache = ACache.get(application);
    mGankData = new MutableLiveData<>();
    mMoreGankData = new MutableLiveData<>();
    mSaveDataResult = new MutableLiveData<>();
    mCompositeDisposable = new CompositeDisposable();
  }

  public LiveData<List<NewGankData.Results>> getGankData() {
    return mGankData;
  }

  public MutableLiveData<List<NewGankData.Results>> getMoreGankData() {
    return mMoreGankData;
  }

  public MutableLiveData<Boolean> getSaveDataResult() {
    return mSaveDataResult;
  }

  public void getGank(final String type, final int flag) {
    if (flag == GankConstant.FLAG_DATA_CACHE) {
      NewGankData data = (NewGankData) mCache.getAsObject(type);
      if (data != null) {
        mGankData.postValue(data.getResults());
        return;
      }
    }
    Observable<NewGankData> getNewData = BuildService.getGankService().getNewData(type, 10, 1);
    getCompositeDisposable().add(getNewData.subscribeOn(Schedulers.io()).subscribe(new Consumer<NewGankData>() {
      @Override
      public void accept(NewGankData newGankData) {
        mGankData.postValue(newGankData.getResults());
        mCache.put(type, newGankData);
      }
    }, new Consumer<Throwable>() {
      @Override
      public void accept(Throwable throwable) {
        LogUtils.e(TAG, throwable.toString());
        mGankData.postValue(null);
      }
    }));
  }

  public void getMoreGank(final String type, final int page) {
    Observable<NewGankData> getMoreData = getGankService().getNewData(type, 10, page);
    getCompositeDisposable().add(getMoreData.subscribeOn(Schedulers.io())
        .subscribe(new Consumer<NewGankData>() {
          @Override
          public void accept(NewGankData newGankData) {
            mMoreGankData.postValue(newGankData.getResults());
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) {
            LogUtils.e(TAG, throwable.toString());
            mGankData.postValue(null);
          }
        }));
  }

  public void saveGank2Db(final NewGankData.Results data) {
    DBHelper dbHelper = new DBHelper(mApplication);
    boolean success = DBTools.insert(dbHelper, data);
    mSaveDataResult.postValue(success);
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    getCompositeDisposable().clear();
  }

  private CompositeDisposable getCompositeDisposable() {
    return mCompositeDisposable;
  }
}
