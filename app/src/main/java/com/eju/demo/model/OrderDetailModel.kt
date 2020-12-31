package com.eju.demo.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.eju.architecture.base.BaseModel
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.network.NetworkUtil
import kotlinx.coroutines.delay

class OrderDetailModel:BaseModel() {


    suspend fun orderDetail(id:String):HelpDetail{
        return callApi {
            delay(2000)
            NetworkUtil.getService(DemoService::class.java).getHelpDetail(id)
        }
    }
}