package com.eju.demo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.databinding.ActivityCacheBinding
import com.eju.demo.viewmodel.CacheViewModel
import kotlinx.android.synthetic.main.activity_cache.*

class CacheActivity : BaseActivity<CacheViewModel,ActivityCacheBinding>(R.layout.activity_cache) {
    override fun setListeners() {

        bt0.setOnClickListener {
            observe(viewModel.data0){
                Log.i("sck220", "setListeners: ${it}")
            }
        }

        bt1.setOnClickListener {
            observe(viewModel.data1){
                Log.i("sck220", "setListeners: ${it}")
            }
        }

        bt2.setOnClickListener {
            observe(viewModel.data2){
                Log.i("sck220", "setListeners: ${it}")
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}