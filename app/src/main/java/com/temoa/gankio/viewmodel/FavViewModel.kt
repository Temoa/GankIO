package com.temoa.gankio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.temoa.gankio.bean.GankData
import com.temoa.gankio.data.repository.FavLocalRepository
import kotlinx.coroutines.launch

/**
 * Created by lai
 * on 2020/8/13.
 */
class FavViewModel(private val repo: FavLocalRepository) : ViewModel() {

  val favLiveData: LiveData<PagingData<GankData>> by lazy { repo.getData().asLiveData() }

  fun insert(gankData: GankData) {
    viewModelScope.launch { repo.insert(gankData) }
  }

  fun delete(gankData: GankData) {
    viewModelScope.launch { repo.delete(gankData) }
  }
}