package com.eju.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.api.HelpDetail
import com.eju.demo.model.OrderDetailRepository

class OrderDetailViewModel(private val orderId:String):BaseViewModel<OrderDetailRepository>() {


    val orderDetail:LiveData<HelpDetail>
        get(){
            return liveData {
                model.orderDetail(orderId)
            }
        }
}