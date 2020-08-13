package com.temoa.gankio

import android.app.Application
import com.temoa.gankio.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by lai
 * on 2020/8/12.
 */
class MyApp : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger(Level.DEBUG)
      androidContext(this@MyApp)
      modules(AppModule.modules)
    }
  }
}