package com.eju.demo.model

import com.eju.architecture.base.BaseRepository
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import kotlinx.coroutines.delay

class OneShotRepository:BaseRepository() {

    suspend fun queryDetail(id:String):HelpDetail{
        delay(2000)
        return getApi(DemoService::class.java).getHelpDetail(id).result
    }
}