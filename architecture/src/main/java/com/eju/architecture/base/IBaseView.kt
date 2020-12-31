package com.eju.architecture.base

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.eju.architecture.ApiExceptionHandler
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
        Log.i("sck220", "toast: ${msg}")
    }

    override fun showError(exception: Exception?) {
        ApiExceptionHandler.handle(exception,obj)
    }

    override fun showLoading(msg: String?) {
        Log.i("sck220", "showLoading: ${msg}")
    }

    override fun hideLoading() {
        Log.i("sck220", "hideLoading: ")
    }

    override fun finishPage() {
        Log.i("sck220", "finishPage: ")
    }

}