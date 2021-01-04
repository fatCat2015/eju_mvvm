package com.eju.architecture

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.eju.architecture.widget.ToastUtil
import com.eju.network.ApiException
import java.lang.Exception

object ApiExceptionHandler {

    fun handle(e: Exception?,any:Any?=null) {
        when{
            e is ApiException ->{
                when(e.code){
                    ApiException.codeIsNull->{
                        ToastUtil.toast(any as? LifecycleOwner?,e.message)
                    }
                    ApiException.kickedOut->{
                        //todo
                    }
                    //todo other code
                }
            }
            "kotlinx.coroutines.JobCancellationException" == e?.cause?.javaClass?.name->{  //job cancel抛出的异常

            }
            else ->{
                ToastUtil.toast(any as? LifecycleOwner?,e?.message)
            }
        }
    }
}