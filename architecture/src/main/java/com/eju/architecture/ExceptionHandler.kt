package com.eju.architecture

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.eju.architecture.widget.ToastUtil
import com.eju.service.ApiException
import com.eju.service.ServiceUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import java.lang.Exception
import java.util.concurrent.CancellationException
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

object ExceptionHandler {

    fun handle(e: Exception?,any:Any?=null) {
        if(e==null){
            return
        }
        when (val exception=ServiceUtil.convertNetException(e)) {
            is ApiException -> {
                when(exception.code){
                    ApiException.codeIsNull->{
                        ToastUtil.toast(any as? LifecycleOwner?,exception.message)
                    }
                    ApiException.kickedOut->{
                        //todo
                    }
                    //todo other code
                    else ->{
                        ToastUtil.toast(any as? LifecycleOwner?,exception.message)
                    }
                }
            }
            is CancellationException -> {  //job cancel抛出的异常 暂不处理

            }
            else -> {
                ToastUtil.toast(any as? LifecycleOwner?,exception?.message)
            }
        }
    }
}