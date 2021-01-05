package com.eju.demo.model

import com.eju.architecture.base.BaseModel
import com.eju.demo.api.DemoService
import com.eju.service.BaseResult
import com.eju.service.PagedList
import kotlinx.coroutines.delay

class ListModel:BaseModel() {


    suspend fun listDemo(): PagedList<String> {
        return execute{
            val list= mutableListOf<String>()
            repeat(10){
                list.add("item${it}")
            }
            delay(2000)
            val pagedList= PagedList(32,list)
            getApi(DemoService::class.java).getHelpDetail("58")
            BaseResult("SYS000","22222",pagedList).result
        }
    }



}