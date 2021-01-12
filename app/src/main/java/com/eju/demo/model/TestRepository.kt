package com.eju.demo.model

import com.eju.architecture.base.BaseRepository
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class TestRepository:BaseRepository() {

//    fun getUserInfo():Flow<HelpDetail>{
//        return firstCacheThenRemote{
//            delay(2000)
//            getApi(DemoService::class.java).getHelpDetail("58a").result
//        }
//    }

}