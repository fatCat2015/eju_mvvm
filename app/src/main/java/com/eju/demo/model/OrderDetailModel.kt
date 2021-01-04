package com.eju.demo.model

import com.eju.architecture.base.BaseModel
import com.eju.architecture.isInUIThread
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.network.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber

class OrderDetailModel:BaseModel() {

    //单接口需要处理返回的数据
    suspend fun orderDetail(id:String):HelpDetail{
        return execute {
            callApi { getApi(DemoService::class.java).getHelpDetail("58") }
                .let {
                    Timber.i("${Thread.currentThread().id}  ${isInUIThread()}")
                    //处理数据
                    it.title="111111111"
                    it
                }
        }
    }

    //单接口 直接返回接口的数据
//    suspend fun orderDetail(id:String):HelpDetail{
//        return callApi { getApi(DemoService::class.java).getHelpDetail("58") }
//    }

}