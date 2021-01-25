package com.eju.architecture.base

import android.os.Bundle
import android.view.MotionEvent
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.eju.architecture.getViewModel
import com.eju.architecture.observe
import com.eju.architecture.widget.NetworkManager
import com.eju.architecture.widget.NetworkState
import com.eju.architecture.util.ReflectUtil
import com.eju.architecture.util.SoftInputUtils
import java.lang.Exception
import java.lang.NullPointerException

abstract class BaseActivity<VM:BaseViewModel<*>,B:ViewDataBinding>(@LayoutRes layoutId:Int):DataBindingActivity<B>(layoutId), IBaseView {

    private val viewImpl: IBaseView by lazy{
        IViewDefaultImpl(this)
    }

    protected open val viewModelCreator:(()->VM)?=null

    private val viewModelDelegate = lazy{
        ReflectUtil.getTypeAt<VM>(javaClass,0)?.let { viewModelClass->
            getViewModel(viewModelClass,viewModelCreator).also {
                lifecycle.addObserver(it)
                observeViewBehavior(it)
            }
        }?:throw NullPointerException("viewModel is null")
    }

    protected val viewModel:VM by viewModelDelegate

    internal open fun observeViewBehavior(viewModel: VM){
        observe(viewModel.exceptionLiveData){
            showError(it)
        }
        observe(viewModel.showLoadingLiveData){
            showLoading(it)
        }
        observe(viewModel.hideLoadingLiveData){
            hideLoading()
        }
        observe(viewModel.toastLiveData){
            toast(it)
        }
        observe(viewModel.finishPageLiveData){
            finishPage()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setListeners()
        initData(savedInstanceState)
        NetworkManager.observe(this){ connected, state->
            onNetworkStateChanged(connected,state)
        }
    }


    abstract fun setListeners()

    abstract fun initData(savedInstanceState: Bundle?)

    open fun onNetworkStateChanged(connected:Boolean,newNetworkState: NetworkState){
    }


    override fun toast(msg: String?) {
        viewImpl.toast(msg)
    }

    override fun showError(exception: Exception?) {
        viewImpl.showError(exception)
    }

    override fun showLoading(msg: String?) {
        viewImpl.showLoading(msg)
    }

    override fun hideLoading() {
        viewImpl.hideLoading()
    }

    override fun finishPage() {
        viewImpl.finishPage()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(viewModelDelegate.isInitialized()){
            lifecycle.removeObserver(viewModel)
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            if(it.action== MotionEvent.ACTION_DOWN){
                if(SoftInputUtils.isShouldHideSoftInput(currentFocus,ev)){
                    SoftInputUtils.hideSoftInput(currentFocus?.windowToken,this)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }


}