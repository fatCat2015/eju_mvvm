package com.eju.demo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.BindingActivity
import com.eju.architecture.getViewModel
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.databinding.ActivityOrderDetailBinding
import com.eju.demo.viewmodel.OrderDetailViewModel
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailAct : BindingActivity<ActivityOrderDetailBinding>(R.layout.activity_order_detail) {

    private var vm:OrderDetailViewModel?=null

    override fun initViewModels() {
        vm=getViewModel(OrderDetailViewModel::class.java)
        binding.vm=vm
    }

    override fun setListeners() {
        bt.setOnClickListener {
            vm?.queryOrderDetail()
        }
        observe(vm?.orderDetail){
            Log.i("sck220", "setListeners: ${it}")
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        val orderId="58"
        vm?.orderId=orderId
        bt.performClick()
    }
}