package com.eju.demo

import android.util.Log
import com.eju.architecture.base.BaseRepository
import com.eju.architecture.launch
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.service.BaseResult
import com.eju.service.awaitResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

object IMContext {

    private val model= ImRepository()

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


class ImRepository:BaseRepository(){

    suspend fun loginIm():HelpDetail{
        return runOnCoroutine {
            delay(2000)
            getApi(DemoService::class.java).getHelpDetail("58").awaitResult()
        }
    }
}