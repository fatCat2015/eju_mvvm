package com.eju.demo.view

import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eju.architecture.base.BasePagingActivity
import com.eju.architecture.router.RouterPath
import com.eju.demo.R
import com.eju.demo.TestAdapter
import com.eju.demo.databinding.ActivityListBinding
import com.eju.demo.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_list.*

@Route(path = RouterPath.Module1.list)
class ListActivity : BasePagingActivity<ListViewModel,ActivityListBinding>(R.layout.activity_list) {

    private var adapter:TestAdapter?=null


    override fun setListeners() {
        bt0.setOnClickListener {
            viewModel.refresh()
        }
        bt1.setOnClickListener {
            viewModel.loadMore()
        }

    }

    override fun initData(savedInstanceState: Bundle?) {
        val a=intent.getStringExtra(ARouter.RAW_URI)
        Log.i("sck220", "initData: ${a}")
        val list=viewModel?.adapterList   //adapter数据源
        adapter=TestAdapter(list).also {
            rv.adapter=it
        }
        bt0.performClick()
    }

    override fun finishRefresh() {
        Log.i("sck220", "finishRefresh: ")
    }

    override fun finishLoadMore() {
        Log.i("sck220", "finishLoadMore: ")
    }

    override fun setEnableLoadMore(enableLoadMore: Boolean) {
        Log.i("sck220", "setEnableLoadMore: $enableLoadMore")
    }

    override fun showEmptyView(showEmpty: Boolean) {
        Log.i("sck220", "showEmptyView: $showEmpty")
    }

    override fun notifyDataSetChanged() {
        Log.i("sck220", "notifyDataSetChanged: ")
        adapter?.notifyDataSetChanged()
    }
}