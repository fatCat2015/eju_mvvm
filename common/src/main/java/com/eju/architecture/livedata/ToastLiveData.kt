package com.eju.architecture.livedata

import android.widget.Toast
import androidx.lifecycle.map
import com.eju.architecture.application

class ToastLiveData:UILiveData<String>() {

    private var short:Boolean=false

    private val toastDelegate = lazy {
        Toast.makeText(application,"",Toast.LENGTH_SHORT)
    }

    private val toast:Toast by toastDelegate

    val toastLiveData=this.map {
        toast.setText(it)
        toast.duration=if(short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
        toast
    }

    fun toast(msg:String?,short:Boolean){
        msg?.let {
            this.short=short
            changeValue(it)
        }
    }

    fun cancel(){
        if(toastDelegate.isInitialized()){
            toast.cancel()
        }
    }

}