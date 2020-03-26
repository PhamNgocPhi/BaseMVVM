package com.example.basemvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm.utils.bus.action.BackAction
import com.example.basemvvm.utils.bus.event.BackEvent
import com.example.basemvvm.utils.constants.ViewState
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding, M : BaseViewModel> : DaggerFragment() {

    protected var binding: T? = null
    protected lateinit var viewModel: M
    protected val sharedViewModel by activityViewModels<SharedViewModel>()

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected abstract fun viewModelClass(): Class<M>

    protected abstract fun handleViewState(viewState: Int)

    protected abstract fun initView()

    private var disposable : Disposable? = null

    protected fun onBackPressed(){
        //do something common if you want.
        //pass data to previous screen.
        Timber.d("onBackPressed in Fragment")
    }

    protected fun onBackResult(data : BackAction){
        //handle data like onActivityResult(if replace fragment - save data to global and handle in onViewCreated)
        Timber.d("onBackResult in Fragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass())
        //Note: làm kiểu này mỗi lúc back lại view sẽ phải khởi tạo lại,
        // có thể lưu view lại cho lần sau(ko bắt buộc, nếu thấy ko cần thiết thì xóa cmt này)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable = BackEvent.instance?.subscribe(Consumer<BackAction> {
            onBackResult(it)
        })

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })

        initView()

        if (savedInstanceState == null) {
            //init state in first time
        } else {
            //fragment added, state back or orientation change.
        }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.run {
                when (viewState) {
                    ViewState.SHOW_LOADING -> (activity as? BaseActivity<*, *>)?.showLoading()
                    ViewState.HIDE_LOADING -> (activity as? BaseActivity<*, *>)?.hideLoading()
                    else -> handleViewState(viewState)
                }
            }
        })
    }

    override fun onDestroyView() {
        if (binding != null) {
            binding?.unbind()
            binding = null
        }
        super.onDestroyView()
    }

    override fun onDestroy() {
        if (disposable != null) {
            disposable?.dispose()
        }
        super.onDestroy()
    }

}