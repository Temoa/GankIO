package com.temoa.gankio.di

import com.temoa.gankio.api.GankService
import com.temoa.gankio.data.local.AppDatabase
import com.temoa.gankio.data.mapper.FavEntity2GankDataMapper
import com.temoa.gankio.data.mapper.GankData2FavEntityMapper
import com.temoa.gankio.data.repository.FavLocalRepository
import com.temoa.gankio.data.repository.GankRemoteRepository
import com.temoa.gankio.viewmodel.FavViewModel
import com.temoa.gankio.viewmodel.GankViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by lai
 * on 2020/8/12.
 */
object AppModule {

  private val networkModule = module {
    single { GankService.buildOkHttpClient() }
    single { GankService.create(get()) }
  }

  private val localModule = module {
    single { AppDatabase.init(androidApplication()) }
  }

  private val repoModule = module {
    single { GankRemoteRepository(get()) }
    single { FavLocalRepository(get(), FavEntity2GankDataMapper(), GankData2FavEntityMapper()) }
  }

  private val viewModeModule = module {
    viewModel { GankViewModel(get()) }
    viewModel { FavViewModel(get()) }
  }

  val modules = listOf(networkModule, localModule, repoModule, viewModeModule)
}