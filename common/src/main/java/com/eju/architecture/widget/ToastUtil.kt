package com.eju.architecture.widget

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.eju.architecture.application
import com.eju.architecture.livedata.ToastLiveData
import com.eju.architecture.livedata.UILiveData
import com.eju.architecture.observe

object ToastUtil {

    private val toastMap:MutableMap<LifecycleOwner,ToastLiveData> by lazy {
        mutableMapOf<LifecycleOwner,ToastLiveData>()
    }

    fun toast(lifecycleOwner: Any?,msg:String?,short:Boolean=true){
        when (lifecycleOwner) {
            !is LifecycleOwner -> {
                toast(msg,short)
            }
            else -> {
                toastMap[lifecycleOwner]?.let { toast->
                    toast.toast(msg,short)
                }?:ToastLiveData().also {toast->
                    toastMap[lifecycleOwner] = toast
                    lifecycleOwner.observe(toast.toastLiveData){
                        it.show()
                    }
                    lifecycleOwner.lifecycle.addObserver(object:DefaultLifecycleObserver{
                        override fun onDestroy(owner: LifecycleOwner) {
                            toast.cancel()
                            toastMap.remove(owner)
                        }
                    })
                    toast.toast(msg,short)
                }
            }
        }
    }

    fun toast(msg:String?, short:Boolean=true){
        if(!msg.isNullOrEmpty()){
            val toast=Toast.makeText(application,"",Toast.LENGTH_SHORT)
            toast.setText(msg)
            toast.duration=if(short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
            toast.show()
        }
    }

}