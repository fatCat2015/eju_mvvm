package com.eju.demo

import android.util.Log
import com.eju.architecture.base.BaseModel
import com.eju.architecture.launch
import com.eju.service.BaseResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

object IMContext {

    private val model= ImModel()

    private var job:Job?=null

    fun loginIm(){
        job=launch(
            onError = {
                Log.i("sck220", "onFailed: ${Thread.currentThread().id}")
                false
            },
            onComplete = {
                Log.i("sck220", "onComplete: ${Thread.currentThread().id}")
            }


        ) { model.loginIm()
            Log.i("sck220", "onSuccess: ${Thread.currentThread().id}")
        }


    }

    fun cancel(){
        job?.cancel()
    }

}


class ImModel:BaseModel(){
    suspend fun loginIm():String{
        return execute {
            delay(2000)
//            BaseResult("SYS0001","11111111","Success")
            BaseResult("SYS000","11111111","Success").result
        }
    }
}