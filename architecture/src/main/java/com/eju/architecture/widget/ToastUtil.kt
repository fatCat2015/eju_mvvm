package com.eju.architecture.widget

import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.eju.architecture.application
import com.eju.architecture.livedata.UILiveData
import com.eju.architecture.observe

object ToastUtil {

    private val toast:Toast by lazy {
        Toast.makeText(application,"",Toast.LENGTH_SHORT)
    }

    fun toast(msg:String?,shortFlag:Boolean=true){
        toast(msg, shortFlag, null)
    }

    fun toast(msg:String?, shortFlag:Boolean=true,lifecycleOwner: LifecycleOwner?=null){
        if(!msg.isNullOrEmpty()){
            when (lifecycleOwner) {
                null -> {
                    with(toast) {
                        setText(msg)
                        duration=if(shortFlag) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
                        show()
                    }
                }
                else ->{
                    _toast(msg, shortFlag, lifecycleOwner)
                }
            }

        }
    }

    private fun _toast(msg:String,shortFlag:Boolean=true,lifecycleOwner: LifecycleOwner){
        val msgLiveData=UILiveData<String>()
        lifecycleOwner.observe(msgLiveData){
                with(toast){
                    setText(msg)
                    duration=if(shortFlag) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
                    show()
                }
        }
        lifecycleOwner.lifecycle.addObserver(object:DefaultLifecycleObserver{
            override fun onDestroy(owner: LifecycleOwner) {
                toast.cancel()
            }
        })
        msgLiveData.changeValue(msg)
    }

}