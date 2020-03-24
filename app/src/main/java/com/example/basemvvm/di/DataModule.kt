package com.example.basemvvm.di

import com.example.basemvvm.data.preferences.AppPreferences
import com.example.basemvvm.data.repository.ApiRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@JvmField
val dataModule = module {
    single { ApiRepository(get()) }
    single { AppPreferences(androidContext()) }
}