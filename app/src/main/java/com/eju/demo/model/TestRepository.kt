package com.eju.demo.model

import android.util.Log
import com.eju.architecture.base.BaseRepository
import com.eju.architecture.isInUIThread
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TestRepository:BaseRepository() {


    suspend fun getUserInfo(id:String):HelpDetail{
        Log.i("sck220", "getUserInfo->getUserInfo: ${isInUIThread()} ")
        delay(2000)
        return getApi(DemoService::class.java).getHelpDetail(id).result
    }

    fun getFollowCount():Flow<Int>{
        return flow {
            while (true){
                Log.i("sck220", "getFollowCount: ${isInUIThread()}")
                delay(2000)
                emit(20)
            }
        }.map {
            it
        }
    }

}