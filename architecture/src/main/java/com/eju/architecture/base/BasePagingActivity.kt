package com.eju.architecture.base

import com.eju.architecture.observe

abstract class BasePagingActivity<VM:BasePagingViewModel<*,*>>(layoutId:Int):BaseActivity<VM>(layoutId),IPagingView {


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