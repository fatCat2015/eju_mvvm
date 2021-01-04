package com.eju.demo.model

import android.util.Log
import com.eju.architecture.asyncSafely
import com.eju.architecture.base.BaseModel
import com.eju.architecture.isInUIThread
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import kotlinx.coroutines.delay
import timber.log.Timber

class MultiModel:BaseModel() {

    suspend fun demo1():HelpDetail{
        return execute{
            delay(2000)
            val firstData= callApi{getApi(DemoService::class.java).getHelpDetail("58")}
            val secondData= callApi{getApi(DemoService::class.java).getHelpDetail("58")}
            //接口顺序执行 firstData ->sendData
            secondData
        }
    }


    suspend fun demo2():String{
        return execute{
            val onePartData0=asyncSafely {
                callApi{getApi(DemoService::class.java).getHelpDetail("58")}
            }
            val onePartData1=asyncSafely() {
                callApi{getApi(DemoService::class.java).getHelpDetail("58")}
            }
            val data0=onePartData0.await()
            val data1=onePartData1.await()
            "111"    //执行到这 两个接口都执行完毕
        }
    }
}