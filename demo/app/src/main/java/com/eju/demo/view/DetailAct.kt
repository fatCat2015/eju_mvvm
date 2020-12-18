package com.eju.demo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.eju.architecture.base.BindingActivity
import com.eju.architecture.getViewModel
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.databinding.ActivityDetailBinding
import com.eju.demo.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailAct : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {


    private var detailVm:DetailViewModel?=null

    override fun initViewModels() {
        val id="58"
        detailVm=getViewModel(DetailViewModel::class.java){
            DetailViewModel(id)
        }
        binding.vm=detailVm //ViewDataBinding中观察数据 触发接口请求
    }

    override fun setListeners() {
        bt0.setOnClickListener {
            tvTitle.text=detailVm?.detail?.value?.title
        }
        observe(detailVm?.detail){
            Log.i("sck220", "setListeners: ${it}")
            //todo 设置其他数据(不易在layout中直接展示的数据)展示
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}