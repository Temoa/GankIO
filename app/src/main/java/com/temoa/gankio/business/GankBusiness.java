package com.temoa.gankio.business;

import android.content.Context;

import com.kunminx.core.bus.Result;
import com.kunminx.core.business.BaseBusiness;
import com.temoa.gankio.bean.NewGankData;
import com.temoa.gankio.db.DBHelper;
import com.temoa.gankio.db.DBTools;
import com.temoa.gankio.network.BuildService;

import org.afinal.simplecache.ACache;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.temoa.gankio.network.BuildService.getGankService;

/**
 * Created by lai
 * on 2019/6/21.
 */
public class GankBusiness extends BaseBusiness<GankBus> implements IGankRequest {

  private Context mContext;
  private ACache mCache;

  private CompositeDisposable mCompositeDisposable;

  private CompositeDisposable getCompositeDisposable() {
    return mCompositeDisposable;
  }

  private int getTypeHashCode(String type) {
    return type == null ? 0 : type.hashCode();
  }

  public void init(Context context) {
    mContext = context;
    mCompositeDisposable = new CompositeDisposable();
  }

  @Override
  public void getGank(final String type, final int flag) {
    handleRequest(new IAsync() {
      @Override
      public Result onExecute(final ObservableEmitter<Result> e) {
        if (flag == GankConstant.FLAG_DATA_CACHE) {
          mCache = ACache.get(mContext);
          NewGankData data = (NewGankData) mCache.getAsObject(type);
          if (data != null) {
            return new Result(GankConstant.RESULT_CODE_GET_NEW_DATA + getTypeHashCode(type), data.getResults());
          }
        }
        Observable<NewGankData> getNewData = BuildService.getGankService().getNewData(type, 10, 1);
        getCompositeDisposable().add(getNewData.subscribe(new Consumer<NewGankData>() {
          @Override
          public void accept(NewGankData newGankData) {
            sendMessage(e, new Result(GankConstant.RESULT_CODE_GET_NEW_DATA + getTypeHashCode(type), newGankData.getResults()));
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) {
            sendMessage(e, new Result(GankConstant.RESULT_CODE_ERROR + getTypeHashCode(type), throwable.getMessage()));
          }
        }));
        return null;
      }
    });
  }

  @Override
  public void getMoreGank(final String type, final int page) {
    handleRequest(new IAsync() {
      @Override
      public Result onExecute(final ObservableEmitter<Result> e) {
        Observable<NewGankData> getMoreData = getGankService().getNewData(type, 10, page);
        getCompositeDisposable().add(getMoreData.subscribeOn(Schedulers.io())
            .subscribe(new Consumer<NewGankData>() {
              @Override
              public void accept(NewGankData newGankData) {
                sendMessage(e, new Result(GankConstant.RESULT_CODE_GET_MORE_DATA + getTypeHashCode(type), newGankData.getResults()));
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) {
                sendMessage(e, new Result(GankConstant.RESULT_CODE_ERROR + getTypeHashCode(type), throwable.getMessage()));
              }
            }));
        return null;
      }
    });
  }

  @Override
  public void saveGank2Db(final String type, final NewGankData.Results data) {
    handleRequest(new IAsync() {
      @Override
      public Result onExecute(ObservableEmitter<Result> e) {
        DBHelper dbHelper = new DBHelper(mContext);
        boolean success = DBTools.insert(dbHelper, data);
        return new Result(GankConstant.RESULT_CODE_INSERT_FAV_DATA + getTypeHashCode(type), success);
      }
    });
  }

  @Override
  public void onDestroy() {
    getCompositeDisposable().dispose();
  }
}
