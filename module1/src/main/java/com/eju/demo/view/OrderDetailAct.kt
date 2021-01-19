package com.eju.demo.view

import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.base.DataBindingActivity
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.databinding.ActivityOrderDetailBinding
import com.eju.demo.viewmodel.OrderDetailViewModel
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailAct : BaseActivity<OrderDetailViewModel,ActivityOrderDetailBinding>(R.layout.activity_order_detail) {


    override val viewModelCreator: (() -> OrderDetailViewModel)?
        get() = {
            OrderDetailViewModel("58")
        }

    override fun setListeners() {
        bt.setOnClickListener {
            viewModel.refreshOrderDetail()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        observe(viewModel.orderDetail){
            binding.detail=it
            Log.i("sck220", "orderDetail: ${it}")
        }
    }
}