package com.eju.architecture

import androidx.lifecycle.LifecycleOwner
import com.eju.architecture.widget.ToastUtil
import com.eju.network.ApiException
import java.lang.Exception

object ApiExceptionHandler {

    fun handle(e: Exception?,any:Any?=null) {
        if(e is ApiException){
            when(e.code){
                ApiException.codeIsNull->{
                    ToastUtil.toast(e.message,lifecycleOwner=any as? LifecycleOwner?)
                }
                ApiException.kickedOut->{
                    //todo
                }
                //todo other code
            }
        }else{
            ToastUtil.toast(e?.message,lifecycleOwner=any as? LifecycleOwner?)
        }
    }
}