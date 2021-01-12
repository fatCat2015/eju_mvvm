package com.eju.demo.model

import android.util.Log
import com.eju.architecture.base.BaseRepository
import kotlinx.coroutines.delay

class SearchRepository:BaseRepository() {

    suspend fun searchList(query:String):List<String>{
        return runOnCoroutine {
            delay(2000)
            Log.i("sck220", "searchList: 执行搜索接口 ")
            mutableListOf("1","2","230")
        }
    }
}