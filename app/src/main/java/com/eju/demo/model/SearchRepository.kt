package com.eju.demo.model

import android.util.Log
import com.eju.architecture.base.BaseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SearchRepository:BaseRepository() {

    suspend fun searchList(query:String):List<String>{
        return execute {
            delay(2000)
            Log.i("sck220", "searchList: 执行搜索接口 ")
            mutableListOf("1","2","230")
        }
    }
}