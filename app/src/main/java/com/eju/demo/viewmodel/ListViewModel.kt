package com.eju.demo.viewmodel

import android.util.Log
import com.eju.architecture.base.BasePagingViewModel
import com.eju.architecture.base.PageParams
import com.eju.demo.model.ListModel
import kotlinx.coroutines.*

class ListViewModel:BasePagingViewModel<ListModel,String>() {



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
            model.listDemo()
        }
    }





}