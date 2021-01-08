package com.eju.architecture.base

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import com.eju.architecture.ExceptionHandler
import com.eju.architecture.isInUIThread
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
        ExceptionHandler.handle(exception,obj)
        Log.i("sck220","showError ${isInUIThread()}")
    }

    override fun showLoading(msg: String?) {
        Log.i("sck220","showLoading ${isInUIThread()}")
    }

    override fun hideLoading() {
        Log.i("sck220","hideLoading ${isInUIThread()}")
    }

    override fun finishPage() {
        if(obj is Activity){
            obj.finish()
        }else if(obj is Fragment){
            obj.activity?.finish()
        }
    }

}