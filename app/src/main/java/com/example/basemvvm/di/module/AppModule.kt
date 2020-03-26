package com.example.basemvvm.di.module

import android.content.Context
import com.example.basemvvm.AppApplication
import com.example.basemvvm.data.preferences.AppPreferences
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideContext(application: AppApplication): Context

    @Binds
    @Singleton
    abstract fun provideAppPreferences(appPreferences: AppPreferences): AppPreferences

}