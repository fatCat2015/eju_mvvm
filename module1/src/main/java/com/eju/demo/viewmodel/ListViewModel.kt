package com.eju.demo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.eju.architecture.base.BasePagingViewModel
import com.eju.architecture.base.PageParams
import com.eju.demo.api.HelpDetail
import com.eju.demo.api.Message
import com.eju.demo.model.ListRepository
import com.eju.service.PagedList
import kotlinx.coroutines.*

class ListViewModel:BasePagingViewModel<ListRepository,Message>() {


    private var job:Job?=null

    override fun getPagedLiveData(pageParams: PageParams): LiveData<PagedList<Message>> {
        job?.cancel()
        return pagedFlowLiveData(onStart = {
            job=it
        }){
            Log.i("sck220", "queryPagedList: ${pageParams}")
            model.listFromCache(pageParams.startIndex,pageParams.pageSize)
        }
//        return pagedLiveData(onStart = {
//            job=it
//        }){
//            Log.i("sck220", "queryPagedList: ${pageParams}")
//            model.list(pageParams.startIndex,pageParams.pageSize)
//        }
    }


}