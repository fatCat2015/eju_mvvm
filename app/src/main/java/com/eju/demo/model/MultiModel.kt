package com.eju.demo.model

import com.eju.architecture.asyncSafely
import com.eju.architecture.base.BaseModel
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.network.NetworkUtil
import kotlinx.coroutines.delay

class MultiModel:BaseModel() {

    suspend fun demo1():HelpDetail{
        return execute{
            delay(2000)
            val firstData= callApi{NetworkUtil.getService(DemoService::class.java).getHelpDetail("58")}
            val secondData= callApi{NetworkUtil.getService(DemoService::class.java).getHelpDetail("58")}
            //接口顺序执行 firstData ->sendData
            secondData
        }
    }


    suspend fun demo2():String{
        return execute{
            val onePartData0=asyncSafely {
                callApi{NetworkUtil.getService(DemoService::class.java).getHelpDetail("58")}
            }
            val onePartData1=asyncSafely() {
                callApi{NetworkUtil.getService(DemoService::class.java).getHelpDetail("58")}
            }
            val data0=onePartData0.await()
            val data1=onePartData1.await()
            "111"    //执行到这 两个接口都执行完毕
        }
    }
}