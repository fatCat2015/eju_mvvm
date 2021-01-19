package com.eju.demo.model

import com.eju.architecture.base.BaseRepository
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.service.awaitResult
import kotlinx.coroutines.delay

class OneShotRepository:BaseRepository() {

    suspend fun queryDetail(id:String):HelpDetail{
        delay(2000)
        return getApi(DemoService::class.java).getHelpDetail(id).awaitResult().let {
            //todo 数据处理
            it
        }
    }
}