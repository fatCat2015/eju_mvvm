package com.eju.demo.model

import com.eju.architecture.base.BaseModel
import com.eju.architecture.isInUIThread
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import timber.log.Timber

class OrderDetailModel:BaseModel() {

//    //单接口需要处理返回的数据
    suspend fun orderDetail1(id:String):HelpDetail{
        return execute {
            getApi(DemoService::class.java).getHelpDetail("58").result
                .let {
                    Timber.i("${Thread.currentThread().id}  ${isInUIThread()}")
                    //处理数据
                    it.title="111111111"
                    it
                }
        }
    }

    //单接口 直接返回接口的数据
    suspend fun orderDetail(id:String):HelpDetail{
        return getApi(DemoService::class.java).getHelpDetail("58").result
    }

}