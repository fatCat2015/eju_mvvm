package com.eju.demo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.activity_test2.*

class TestActivity : BaseActivity<TestViewModel>(R.layout.activity_test2) {

    override fun setListeners() {
        bt1.setOnClickListener {
            observe(viewModel.aa()){
                Log.i("sck220", "setListeners: ${it}")
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}