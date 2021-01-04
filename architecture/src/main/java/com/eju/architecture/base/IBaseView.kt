package com.eju.architecture.base

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.eju.architecture.ApiExceptionHandler
import com.eju.architecture.widget.ToastUtil
import timber.log.Timber
import java.lang.Exception

interface IBaseView {
    fun showLoading(msg:String?=null)

    fun hideLoading()

    fun toast(msg:String?=null)

    fun showError(exception: Exception?)

    fun finishPage()

}

class IViewDefaultImpl(private val obj:Any?): IBaseView {

    override fun toast(msg: String?) {
        ToastUtil.toast(obj,msg)
    }

    override fun showError(exception: Exception?) {
        ApiExceptionHandler.handle(exception,obj)
    }

    override fun showLoading(msg: String?) {
        Timber.i("showLoading")
    }

    override fun hideLoading() {
        Timber.i("hideLoading")
    }

    override fun finishPage() {
        if(obj is Activity){
            obj.finish()
        }else if(obj is Fragment){
            obj.activity?.finish()
        }
    }

}