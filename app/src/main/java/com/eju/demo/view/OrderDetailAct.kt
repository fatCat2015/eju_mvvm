package com.eju.demo.view

import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.BaseBindingActivity
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.databinding.ActivityOrderDetailBinding
import com.eju.demo.viewmodel.OrderDetailViewModel
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailAct : BaseBindingActivity<OrderDetailViewModel,ActivityOrderDetailBinding>(R.layout.activity_order_detail) {




    override fun setListeners() {
        bt.setOnClickListener {
            viewModel.queryOrderDetail()

        }
        observe(viewModel.orderDetail){
            Log.i("sck220", "setListeners: ${it}")
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        binding.vm=viewModel
        val orderId="58"
        viewModel.orderId=orderId
        bt.performClick()
    }
}