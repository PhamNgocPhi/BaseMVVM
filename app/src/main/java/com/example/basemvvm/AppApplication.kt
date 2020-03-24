package com.example.basemvvm

import android.app.Application
import com.example.basemvvm.di.dataModule
import com.example.basemvvm.di.networkModule
import com.example.basemvvm.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(listOf(dataModule, viewModelModule, networkModule))
        }

    }

}