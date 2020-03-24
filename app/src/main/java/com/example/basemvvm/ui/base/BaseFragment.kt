package com.example.basemvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm.utils.constants.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var binding: T
    protected lateinit var viewModel: VM
    protected val sharedViewModel by sharedViewModel<SharedViewModel>()

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected abstract fun viewModelClass(): Class<VM>

    protected abstract fun handleViewState(viewState: Int)

    protected abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this).get(viewModelClass())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.run {
                when (viewState) {
                    ViewState.SHOW_LOADING -> showLoading()
                    ViewState.HIDE_LOADING -> hideLoading()
                    else -> handleViewState(viewState)
                }
            }
        })
    }

    fun showLoading() {
        (activity as? BaseActivity<*, *>)?.showLoading()
    }

    fun hideLoading() {
        (activity as? BaseActivity<*, *>)?.hideLoading()
    }

}