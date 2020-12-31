package com.eju.demo

import android.util.Log
import com.eju.architecture.ResultCallback
import com.eju.architecture.base.BaseModel
import com.eju.architecture.launch
import com.eju.network.BaseResult
import kotlinx.coroutines.delay

object IMContext {

    private val model= ImModel()

    fun loginIm(){
        launch(
            { model.loginIm()
            },
            object:ResultCallback<String>{
                override fun onSuccess(data: String) {
                    Log.i("sck220", "onSuccess: ${Thread.currentThread().id}")
                }
                override fun onFailed(e: Throwable): Boolean {
                    e.printStackTrace()
                    Log.i("sck220", "onFailed: ${Thread.currentThread().id}")
                    return super.onFailed(e)
                }

                override fun onComplete() {
                    Log.i("sck220", "onComplete: ${Thread.currentThread().id}")
                    super.onComplete()
                }
            })

    }
}


class ImModel:BaseModel(){
    suspend fun loginIm():String{
        return callApi {
            delay(2000)
//            BaseResult("SYS0001","11111111","Success")
            BaseResult("SYS000","11111111","Success")
        }
    }
}