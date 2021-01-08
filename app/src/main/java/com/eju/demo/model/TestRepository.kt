package com.eju.demo.model

import com.eju.architecture.base.BaseRepository
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.service.cache.CacheConfig
import com.eju.service.cache.CacheStrategy
import kotlinx.coroutines.flow.Flow

class TestRepository:BaseRepository() {


    fun getUserInfo():Flow<HelpDetail>{
        return getDataFromCache(key="a22bcdef11g"){
            getApi(DemoService::class.java).getHelpDetail("58").result
        }
    }

}