package com.eju.architecture.base

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import java.lang.Exception

interface IView {
    fun showLoading(msg:String?=null)

    fun hideLoading()

    fun toast(msg:String?=null)

    fun showError(exception: Exception?)

    fun finishPage()

}

class IViewDefaultImpl(activity: Activity?): IView {

    constructor(fragment: Fragment):this(fragment.activity){

    }

    override fun toast(msg: String?) {
        Log.i("sck220", "toast: ${msg}")
    }

    override fun showError(exception: Exception?) {
        Log.i("sck220", "showError: ${exception?.message}")
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