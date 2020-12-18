package com.eju.architecture.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

abstract class BaseActivity(private val layoutId:Int):AppCompatActivity(),
    IView {

    private val viewImpl: IView by lazy{
        IViewDefaultImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(layoutId!=0){
            setContentView(layoutId)
        }
        initViewModels()
        setListeners()
        initData(savedInstanceState)
    }


    abstract fun initViewModels()

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

}