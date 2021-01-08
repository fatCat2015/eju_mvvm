package com.eju.demo.model

import com.eju.architecture.asyncSafely
import com.eju.architecture.base.BaseRepository
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import kotlinx.coroutines.delay

class MultiRepository:BaseRepository() {

    suspend fun demo1():HelpDetail{
        return execute{
            delay(2000)
            val firstData= getApi(DemoService::class.java).getHelpDetail("58").result
            val secondData= getApi(DemoService::class.java).getHelpDetail("58").result
            //接口顺序执行 firstData ->sendData
            secondData
        }
    }


    suspend fun demo2():String{
        return execute{
            val onePartData0=asyncSafely {
                getApi(DemoService::class.java).getHelpDetail("58").result
            }
            val onePartData1=asyncSafely() {
                getApi(DemoService::class.java).getHelpDetail("58").result
            }
            val data0=onePartData0.await()
            val data1=onePartData1.await()
            "111"    //执行到这 两个接口都执行完毕
        }
    }
}