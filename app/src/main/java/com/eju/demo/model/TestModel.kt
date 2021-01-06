package com.eju.demo.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import androidx.core.net.ConnectivityManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.eju.architecture.application
import com.eju.architecture.base.BaseModel
import com.eju.demo.api.DemoService
import kotlinx.coroutines.*

class TestModel:BaseModel() {


    fun test():LiveData<String>{

        GlobalScope.launch (context= CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i("sck220", "test11≈: ${throwable.message}")
        }){
            execute {
                val a=async {
                    getApi(DemoService::class.java).getHelpDetail("58")
                }
                Log.i("sck220", "test: 00000000000000000")
                val b=async {
                    getApi(DemoService::class.java).getHelpDetail("58")
                }
                Log.i("sck220", "test: 111111")
                Log.i("sck220", "test1111111111111: ${a.await()}")
            }
        }
        return liveData (context= CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i("sck220", "test: ${throwable.message}")
        }){
            emit("111")
            getApi(DemoService::class.java).getHelpDetail("58")
            delay(3000)
            emit("222")
        }


    }

}