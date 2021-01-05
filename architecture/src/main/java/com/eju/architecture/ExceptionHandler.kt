package com.eju.architecture

import androidx.lifecycle.LifecycleOwner
import com.eju.architecture.widget.ToastUtil
import com.eju.service.ApiException
import com.eju.service.ServiceUtil
import java.lang.Exception
import java.util.concurrent.CancellationException

object ExceptionHandler {

    fun handle(e: Exception?,any:Any?=null) {
        if(e==null){
            return
        }
        val exception=ServiceUtil.convertNetException(e)
        when{
            exception is ApiException ->{
                when(exception.code){
                    ApiException.codeIsNull->{
                        ToastUtil.toast(any as? LifecycleOwner?,e.message)
                    }
                    ApiException.kickedOut->{
                        //todo
                    }
                    //todo other code
                    else ->{
                        ToastUtil.toast(any as? LifecycleOwner?,e.message)
                    }
                }
            }
            e is CancellationException->{  //job cancel抛出的异常 暂不处理

            }
            else ->{
                ToastUtil.toast(any as? LifecycleOwner?,exception?.message)
            }
        }
    }
}