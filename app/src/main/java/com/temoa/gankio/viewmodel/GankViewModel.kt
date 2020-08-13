package com.temoa.gankio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.temoa.gankio.bean.GankData
import com.temoa.gankio.data.repository.GankRemoteRepository

/**
 * Created by lai
 * on 2020/8/12.
 */
class GankViewModel(repo: GankRemoteRepository) : ViewModel() {

  val typeLiveData: MutableLiveData<String> = MutableLiveData()

  val gankLiveData: LiveData<PagingData<GankData>> by lazy { repo.getGank(typeLiveData.value!!).asLiveData() }
}