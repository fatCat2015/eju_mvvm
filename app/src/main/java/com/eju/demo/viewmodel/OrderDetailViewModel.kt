package com.eju.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.api.DemoService
import com.eju.demo.api.HelpDetail
import com.eju.demo.model.OrderDetailModel
import com.eju.network.NetworkUtil
import kotlinx.coroutines.delay

class OrderDetailViewModel:BaseViewModel<OrderDetailModel>() {

    var orderId:String?=null

    val orderDetail= MutableLiveData<HelpDetail>()




    fun queryOrderDetail(){
        launch{
            val data=model.orderDetail(orderId!!)
            orderDetail.value=data
        }
    }
}