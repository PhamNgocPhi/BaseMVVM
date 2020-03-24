package com.example.basemvvm.di

import android.content.Context
import com.example.basemvvm.BuildConfig
import com.example.basemvvm.data.repository.IApiRepository
import com.example.basemvvm.utils.constants.Constant
import com.example.basemvvm.utils.network.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { provideRetrofit(get()) }
    factory { provideApiService(get()) }
    factory { provideHttpClient(androidContext()) }
}


fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


fun provideApiService(retrofit: Retrofit): IApiRepository {
    return retrofit.create<IApiRepository>(IApiRepository::class.java)
}


fun provideHttpClient(context: Context): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(NetworkInterceptor(context))
        .connectTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .build()
}