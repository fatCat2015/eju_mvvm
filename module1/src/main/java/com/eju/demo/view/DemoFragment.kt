package com.eju.demo.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eju.architecture.base.BasePagingLazyLoadFragment
import com.eju.architecture.dialog.BaseDialog
import com.eju.demo.R
import com.eju.demo.TestAdapter
import com.eju.demo.databinding.FragmentDemoBinding
import com.eju.demo.viewmodel.FragmentViewModel
import kotlinx.android.synthetic.main.fragment_demo.*

class DemoFragment:BasePagingLazyLoadFragment<FragmentViewModel,FragmentDemoBinding>(R.layout.fragment_demo) {

    private var adapter:TestAdapter?=null

    companion object{
        fun newInstance(index:Int):DemoFragment {
            return DemoFragment().apply {
                arguments=Bundle().apply {
                    putInt("index",index)
                }
            }
        }
    }

    override fun lazyLoad(savedInstanceState: Bundle?) {
        tvDetail.text=arguments?.getInt("index")?.toString()
        bt0.setOnClickListener {
            viewModel.refresh()
        }

        bt1.setOnClickListener {
            viewModel.loadMore()
        }
        rv.adapter=TestAdapter(viewModel.adapterList).also { adapter=it }
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