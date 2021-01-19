package com.eju.demo.model

import com.eju.architecture.asyncSafely
import com.eju.architecture.base.BaseRepository
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.service.awaitResult
import kotlinx.coroutines.delay

class MultiRepository:BaseRepository() {

    suspend fun demo1():HelpDetail{
        return runOnCoroutine{
            delay(2000)
            val firstData= getApi(DemoService::class.java).getHelpDetail("58").awaitResult()
            val secondData= getApi(DemoService::class.java).getHelpDetail("58").awaitResult()
            //接口顺序执行 firstData ->sendData
            secondData
        }
    }


    suspend fun demo2():String{
        return runOnCoroutine{
            val onePartData0=asyncSafely {
                delay(2000)
                getApi(DemoService::class.java).getHelpDetail("58").awaitResult()
            }
            val onePartData1=asyncSafely() {
                delay(2000)
                getApi(DemoService::class.java).getHelpDetail("58").awaitResult()
            }
            val data0=onePartData0.await()
            val data1=onePartData1.await()
            "111"    //执行到这 两个接口都执行完毕
        }
    }
}