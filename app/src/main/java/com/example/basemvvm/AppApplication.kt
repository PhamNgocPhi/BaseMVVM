package com.example.basemvvm

import com.example.basemvvm.di.component.AppComponent
import com.example.basemvvm.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class AppApplication : DaggerApplication(){

    //what is the purpose of instance variable?
    lateinit var instance : AppApplication

    override fun onCreate() {
        super.onCreate()
        instance = this

        //use timber instead Log
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: AppComponent = DaggerAppComponent.builder().application(this).build()
        component.inject(this)
        return component
    }

}