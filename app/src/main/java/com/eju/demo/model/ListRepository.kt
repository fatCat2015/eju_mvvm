package com.eju.demo.model

import android.util.Log
import com.eju.architecture.base.BaseRepository
import com.eju.architecture.isInUIThread
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.demo.api.Message
import com.eju.service.PagedList
import com.eju.service.awaitResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ListRepository:BaseRepository() {

    fun listFromCache(startIndex:Int,count:Int):Flow<PagedList<Message>>{
        return firstCacheThenRemote{
            getApi(DemoService::class.java).msgList(startIndex,count)
        }
    }

    suspend fun list(startIndex:Int,count:Int):PagedList<Message>{
        return getApi(DemoService::class.java).msgList(startIndex,count).awaitResult()

    }

}