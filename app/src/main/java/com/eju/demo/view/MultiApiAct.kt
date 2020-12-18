package com.eju.demo.view

import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.getViewModel
import com.eju.demo.R
import com.eju.demo.viewmodel.MultiViewModel
import kotlinx.android.synthetic.main.activity_cocurrent_api.*

class MultiApiAct : BaseActivity(R.layout.activity_cocurrent_api) {

    private var vm:MultiViewModel?=null


    override fun initViewModels() {
        vm=getViewModel(MultiViewModel::class.java)
    }

    override fun setListeners() {
        bt0.setOnClickListener {
            vm?.demo2()
        }
        bt1.setOnClickListener {
            vm?.demo1()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}