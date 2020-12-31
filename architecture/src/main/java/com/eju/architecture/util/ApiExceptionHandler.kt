package com.eju.architecture.util

import com.eju.network.ApiException
import java.lang.Exception

class ApiExceptionHandler {

    fun handle(e: Exception?) {
        if(e is ApiException){
            when(e.code){
                ApiException.codeIsNull->{
                    ToastUtil.toast(e.message)
                }
                ApiException.kickedOut->{
                    //todo
                }
                //todo other code
            }
        }else{
            ToastUtil.toast(e?.message)
        }
    }
}