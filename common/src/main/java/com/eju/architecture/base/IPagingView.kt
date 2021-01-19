package com.eju.architecture.base

interface IPagingView {

    fun finishRefresh()

    fun finishLoadMore()

    fun setEnableLoadMore(enableLoadMore:Boolean)

    fun showEmptyView(showEmpty:Boolean)

    fun notifyDataSetChanged()
}