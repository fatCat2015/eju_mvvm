package com.eju.demo.viewmodel

import android.util.Log
import com.eju.architecture.base.BasePagingViewModel
import com.eju.architecture.base.PageParams
import com.eju.demo.api.DemoService
import com.eju.network.AppResponse
import com.eju.network.NetworkUtil
import com.eju.network.PagedList
import kotlinx.coroutines.*

class ListViewModel:BasePagingViewModel<String>() {



    var keyword:String?=null
        set(value) {
            field=value
            refresh()
        }

    private var job:Job?=null



    override fun loadPagedData(pageParams: PageParams) {
        job?.cancel()
        job=pagedApiCall{
            Log.i("sck220", "queryPagedList: ${pageParams}  ${keyword}")
            NetworkUtil.getService(DemoService::class.java).getHelpDetail("58")
            listDemo()
        }
    }




    private suspend fun listDemo():AppResponse<PagedList<String>>{
        return withContext(Dispatchers.IO){
            val list= mutableListOf<String>()
            repeat(10){
                list.add("item${it}")
            }
            delay(2000)
            val pagedList=PagedList(32,list)
            AppResponse("SYS000","",pagedList)
        }
    }


}