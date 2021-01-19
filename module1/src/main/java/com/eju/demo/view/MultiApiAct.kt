package com.eju.demo.view

import android.app.Activity
import android.content.Intent
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.getViewModel
import com.eju.architecture.observe
import com.eju.architecture.router.RouterPath
import com.eju.demo.R
import com.eju.demo.databinding.ActivityCocurrentApiBinding
import com.eju.demo.viewmodel.MultiViewModel
import kotlinx.android.synthetic.main.activity_cocurrent_api.*
import kotlinx.android.synthetic.main.activity_test.*

@Route(path = RouterPath.Module1.multi)
class MultiApiAct : BaseActivity<MultiViewModel,ActivityCocurrentApiBinding>(R.layout.activity_cocurrent_api) {

    @Autowired(name = "bb")
    @JvmField
    var str:String?=null


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
        Log.i("sck220", "initData: $str")
        setResult(Activity.RESULT_OK, Intent().putExtra("aa",20))
    }
}