package com.eju.architecture.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.eju.architecture.observe

abstract class BasePagingFragment<VM:BasePagingViewModel<*,*>,B:ViewDataBinding>(@LayoutRes layoutId:Int):BaseFragment<VM,B>(layoutId),IPagingView {


    override fun observeViewBehavior(viewModel: VM) {
        super.observeViewBehavior(viewModel)
        observe(viewModel.finishRefreshLiveData){
            finishRefresh()
        }
        observe(viewModel.finishLoadMoreLiveData) {
            finishLoadMore()
        }
        observe(viewModel.enableLoadMoreLiveData){
            setEnableLoadMore(it)
        }
        observe(viewModel.showEmptyViewLiveData){
            showEmptyView(it)
        }
        observe(viewModel.notifyDataSetLiveData){
            notifyDataSetChanged()
        }
    }
}