package com.example.basemvvm.ui.list_category

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.basemvvm.R
import com.example.basemvvm.databinding.FragmentListCategoryBinding
import com.example.basemvvm.ui.adapter.ListCategoryAdapter
import com.example.basemvvm.ui.base.BaseFragment
import com.example.basemvvm.utils.constants.ViewState
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ListCategoryFragment : BaseFragment<FragmentListCategoryBinding, ListCategoryViewModel>() {

    override fun layoutRes(): Int {
        return R.layout.fragment_list_category
    }

    override fun viewModelClass(): Class<ListCategoryViewModel> {
        return ListCategoryViewModel::class.java
    }

    override fun handleViewState(viewState: Int) {
        when (viewState) {
            ViewState.ListCategory.GET_LIST_CATERORY_SUCCESS -> hideLoading()
        }
    }

    override fun initView() {
        val adapter = ListCategoryAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.getListCategory().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}
