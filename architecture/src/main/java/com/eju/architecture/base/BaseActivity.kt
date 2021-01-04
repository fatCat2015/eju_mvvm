package com.eju.architecture.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eju.architecture.getViewModel
import com.eju.architecture.livedata.UILiveData
import com.eju.architecture.observe
import com.eju.architecture.util.ReflectUtil
import java.lang.Exception
import java.lang.NullPointerException
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM:BaseViewModel<*>>(private val layoutId:Int):AppCompatActivity(), IBaseView {

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
        if(layoutId!=0){
            setContentView(layoutId)
        }
        setListeners()
        initData(savedInstanceState)
    }


    abstract fun setListeners()

    abstract fun initData(savedInstanceState: Bundle?)

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

}