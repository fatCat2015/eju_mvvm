package com.eju.demo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.model.SearchRepository
import kotlinx.coroutines.Job

class SearchViewModel:BaseViewModel<SearchRepository>() {

    val query=MutableLiveData<String>()

    private var searchJob: Job?=null


    val list:LiveData<List<String>> = query.switchMap {query->
        Log.i("sck220", "关键字发生改变: ${query}")
        searchJob?.cancel()  //取消正在进行的接口
        liveData(showLoading = false) {job->
            searchJob=job   //保存job
            model.searchList(query)    //执行搜索接口
        }
    }
}