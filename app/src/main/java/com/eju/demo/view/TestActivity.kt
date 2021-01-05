package com.eju.demo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.isInUIThread
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.activity_test2.*
import kotlinx.coroutines.*

class TestActivity : BaseActivity<TestViewModel>(R.layout.activity_test2) {

    override fun setListeners() {
        bt1.setOnClickListener {
            c1.cancel()
        }
    }

    val scope= CoroutineScope(Dispatchers.Main)

    val c1=scope.launch {
        delay(2000)
        ensureActive()
        Log.i("sck220", "initData: 111")
        "111"
    }

    val c2=scope.launch {
        delay(3000)
        Log.i("sck220", "initData: 222")
    }


    override fun initData(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            cancel()

        }








    }

}