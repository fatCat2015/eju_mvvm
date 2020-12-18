package com.eju.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.network.NetworkUtil

class DetailViewModel(private val id:String):BaseViewModel() {

    val detail=createApiLiveData {
        NetworkUtil.getService(DemoService::class.java).getHelpDetail(id)
    }

}