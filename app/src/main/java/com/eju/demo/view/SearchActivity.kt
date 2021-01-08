package com.eju.demo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity<SearchViewModel>(R.layout.activity_search) {

    override fun setListeners() {
        bt.setOnClickListener {
            viewModel.query.value="关键字改变"
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        observe(viewModel.list){
            Log.i("sck220", "list changed: ${it}")
        }
    }
}