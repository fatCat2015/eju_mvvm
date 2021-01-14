package com.eju.demo.view

import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.getViewModel
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.databinding.ActivityCocurrentApiBinding
import com.eju.demo.viewmodel.MultiViewModel
import kotlinx.android.synthetic.main.activity_cocurrent_api.*
import kotlinx.android.synthetic.main.activity_test.*

class MultiApiAct : BaseActivity<MultiViewModel,ActivityCocurrentApiBinding>(R.layout.activity_cocurrent_api) {




    override fun setListeners() {
        bt0.setOnClickListener {
            observe(viewModel.neededData){
                Log.i("sck220", "demo1: ${it}")
            }
        }
        bt1.setOnClickListener {
            observe(viewModel.neededData1){
                Log.i("sck220", "demo2: ${it}")
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}