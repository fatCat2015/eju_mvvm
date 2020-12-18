package com.eju.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.network.NetworkUtil
import kotlinx.coroutines.delay

class OrderDetailViewModel:BaseViewModel() {

    var orderId:String?=null

    val orderDetail= MutableLiveData<HelpDetail>()


    fun queryOrderDetail(){
        orderId?.let {
            apiCall({
                delay(2000)
                NetworkUtil.getService(DemoService::class.java).getHelpDetail(it)
            })  {

                orderDetail.postValue(it)
            }

        }
    }
}