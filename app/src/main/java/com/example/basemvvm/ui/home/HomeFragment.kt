package com.example.basemvvm.ui.home

import com.example.basemvvm.R
import com.example.basemvvm.databinding.HomeFragmentBinding
import com.example.basemvvm.ui.base.BaseFragment

class HomeFragment : BaseFragment<HomeFragmentBinding,HomeViewModel>() {

    override fun layoutRes(): Int {
        return R.layout.home_fragment;
    }

    override fun viewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun handleViewState(viewState: Int) {

    }

    override fun initView() {

    }

}
