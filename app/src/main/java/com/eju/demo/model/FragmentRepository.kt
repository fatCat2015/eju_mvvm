package com.eju.demo.model

import com.eju.architecture.base.BaseRepository
import com.eju.demo.api.DemoService
import com.eju.demo.api.Message
import com.eju.service.PagedList
import com.eju.service.awaitResult

class FragmentRepository:BaseRepository() {

    suspend fun msgList(start_index:Int,count:Int):PagedList<Message>{
        return getApi(DemoService::class.java).msgList(start_index,count).awaitResult()
    }

}