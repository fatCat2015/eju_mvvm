package com.eju.demo.view

import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.getViewModel
import com.eju.demo.R
import com.eju.demo.viewmodel.MultiViewModel
import kotlinx.android.synthetic.main.activity_cocurrent_api.*

class MultiApiAct : BaseActivity<MultiViewModel>(R.layout.activity_cocurrent_api) {




    override fun setListeners() {
        bt0.setOnClickListener {
            viewModel.demo1()
        }
        bt1.setOnClickListener {
            viewModel.demo2()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}