package com.example.basemvvm.ui.list_category

import androidx.lifecycle.MutableLiveData
import com.example.basemvvm.data.model.Category
import com.example.basemvvm.data.preferences.AppPreferences
import com.example.basemvvm.data.repository.ApiRepository
import com.example.basemvvm.ui.base.BaseViewModel
import com.example.basemvvm.utils.constants.ViewState
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.addTo
import org.koin.java.KoinJavaComponent.inject

class ListCategoryViewModel : BaseViewModel() {

    private val apiRepository: ApiRepository by inject(ApiRepository::class.java)
    private val appPreferences: AppPreferences by inject(AppPreferences::class.java)

    private val listCategory = MutableLiveData<List<Category>>()

    fun getListCategory(): MutableLiveData<List<Category>> {
        apiRepository.getListCategory()
            .doOnSubscribe(Consumer { viewState.value = ViewState.SHOW_LOADING })
            .doFinally { viewState.value = ViewState.HIDE_LOADING }
            .subscribe(
                {
                    viewState.value = ViewState.ListCategory.GET_LIST_CATERORY_SUCCESS
                    listCategory.value = it?.data?.categories
                    appPreferences.saveAccessToken("test")
                },
                { throwable -> viewState.value = ViewState.SHOW_ERROR }
            )
            .addTo(disposable)
        return listCategory
    }

}