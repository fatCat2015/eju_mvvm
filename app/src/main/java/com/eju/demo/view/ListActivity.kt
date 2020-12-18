package com.eju.demo.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.eju.architecture.base.BaseActivity
import com.eju.architecture.base.IPagingView
import com.eju.architecture.getViewModel
import com.eju.architecture.observe
import com.eju.demo.R
import com.eju.demo.TestAdapter
import com.eju.demo.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity(R.layout.activity_list),IPagingView {

    private var vm:ListViewModel?=null
    private var adapter:TestAdapter?=null

    override fun initViewModels() {
        vm=getViewModel(ListViewModel::class.java)
    }

    override fun setListeners() {
        bt0.setOnClickListener {
            vm?.refresh()
        }
        bt1.setOnClickListener {
            vm?.loadMore()

        }


        et.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                vm?.keyword=s?.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        val list=vm?.adapterList   //adapter数据源
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