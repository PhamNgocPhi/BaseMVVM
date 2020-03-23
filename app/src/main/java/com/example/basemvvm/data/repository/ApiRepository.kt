package com.example.basemvvm.data.repository

import com.example.basemvvm.data.model.ApiObjectResponse
import com.example.basemvvm.data.model.CategoryResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiRepository: IApiRepository) {

    fun getListCategory(): Single<ApiObjectResponse<CategoryResponse>> {
        return apiRepository.getListCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}