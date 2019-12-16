package com.temoa.gankio.business

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.temoa.gankio.bean.NewGankData
import com.temoa.gankio.bean.NewGankData.Results
import com.temoa.gankio.db.DBHelper
import com.temoa.gankio.db.DBTools
import com.temoa.gankio.network.BuildService
import com.temoa.gankio.tools.LogUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.afinal.simplecache.ACache

/**
 * Created by lai
 * on 2019/7/22.
 */
class GankViewModel(private val mApplication: Application) : AndroidViewModel(mApplication) {

  private val mCache: ACache = ACache.get(mApplication)
  private val compositeDisposable: CompositeDisposable = CompositeDisposable()
  private val mGankData: MutableLiveData<List<Results>> = MutableLiveData()
  val moreGankData: MutableLiveData<List<Results>> = MutableLiveData()
  val saveDataResult: MutableLiveData<Boolean> = MutableLiveData()

  val gankData: LiveData<List<Results>>
    get() = mGankData

  fun getGank(type: String, flag: Int) {
    if (flag == GankConstant.FLAG_DATA_CACHE) {
      val data = mCache.getAsObject(type) as NewGankData
      mGankData.postValue(data.results)
      return
    }
    val getNewData = BuildService.gankService.getNewData(type, 10, 1)
    compositeDisposable.add(getNewData.subscribeOn(Schedulers.io()).subscribe({ newGankData ->
      mGankData.postValue(newGankData.results)
      mCache.put(type, newGankData)
    }) { throwable ->
      LogUtils.e(TAG, throwable.toString())
      mGankData.postValue(null)
    })
  }

  fun getMoreGank(type: String, page: Int) {
    val getMoreData = BuildService.gankService.getNewData(type, 10, page)
    compositeDisposable.add(getMoreData.subscribeOn(Schedulers.io())
        .subscribe({ newGankData -> moreGankData.postValue(newGankData.results) }) { throwable ->
          LogUtils.e(TAG, throwable.toString())
          mGankData.postValue(null)
        })
  }

  fun saveGank2Db(data: Results) {
    val dbHelper = DBHelper(mApplication)
    val success = DBTools.insert(dbHelper, data)
    saveDataResult.postValue(success)
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }

  companion object {
    private const val TAG = "GankViewModel"
  }

}