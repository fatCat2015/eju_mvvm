package com.eju.demo.model

import android.util.Log
import com.eju.architecture.base.BaseRepository
import com.eju.architecture.isInUIThread
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.service.awaitResult
import kotlinx.coroutines.delay

class OrderDetailRepository:BaseRepository() {

    private var count=0

    //返回接口的数据 并且进行处理的数据
    suspend fun orderDetail(id:String):HelpDetail{
        delay(2000)
        return getApi(DemoService::class.java).getHelpDetail1(id).result
            //处理数据
            .let {
                it.title="111111111"
                it.address="地址:${count++}"
                it
            }
    }



}