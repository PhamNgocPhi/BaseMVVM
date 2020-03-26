package com.example.basemvvm.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm.utils.constants.ViewState
import com.example.basemvvm.utils.network.NetworkEvent
import com.example.basemvvm.utils.network.NetworkState
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, M : BaseViewModel> : DaggerAppCompatActivity() {

    protected var binding: T? = null
    protected lateinit var viewModel: M

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected abstract fun viewModelClass(): Class<M>

    protected abstract fun handleViewState()

    protected abstract fun initView()

    abstract fun showLoading()

    abstract fun hideLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes());
        binding?.lifecycleOwner = this
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass())
        initView()
        viewModel.viewState.observe(this, Observer { viewState ->
            viewState?.run {
                when (viewState) {
                    ViewState.SHOW_LOADING -> showLoading()
                    ViewState.HIDE_LOADING -> hideLoading()
                    else -> handleViewState()
                }
            }
        })
    }

    /**
     * we register the BaseActivity as subscriber
     * and specify what needs to be done in case of NetworkState
     */
    override fun onResume() {
        super.onResume()
        NetworkEvent.register(this, Consumer {
            when (it) {
                //TODO handle network state in here
                NetworkState.NO_INTERNET -> Toast.makeText(
                    this,
                    "NO internet",
                    Toast.LENGTH_SHORT
                ).show()
//                NetworkState.NOT_FOUND -> // do something
//                NetworkState.FORBIDDEN -> // do something
//                NetworkState.BAD_REQUEST -> // do something
//                NetworkState.UNAUTHORISED -> // do something
            }
        })

    }

    override fun onStop() {
        super.onStop()
        NetworkEvent.unregister(this)
    }

    override fun onDestroy() {
        if (binding != null) {
            binding?.unbind()
            binding = null
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Timber.d("onBackPressed in activity")
    }

}