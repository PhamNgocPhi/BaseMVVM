package com.example.basemvvm.di

import com.example.basemvvm.ui.list_category.ListCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val viewModelModule = module {
    viewModel {
        ListCategoryViewModel()
    }
}