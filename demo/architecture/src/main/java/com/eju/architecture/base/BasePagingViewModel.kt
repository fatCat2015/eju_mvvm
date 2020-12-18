package com.eju.architecture.base

import androidx.lifecycle.MutableLiveData
import com.eju.architecture.livedata.CountLiveData
import com.eju.architecture.livedata.UILiveData
import com.eju.network.AppResponse
import com.eju.network.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.lang.Exception

abstract class BasePagingViewModel<T>(private val startPage:Int=0,private val pageSize:Int=10) :
    BaseViewModel(),IPagingView{

    internal val finishRefreshLiveData=
        CountLiveData()

    internal val finishLoadMoreLiveData=
        CountLiveData()

    internal val enableLoadMoreLiveData=UILiveData<Boolean>()

    internal val notifyDataSetLiveData=
        CountLiveData()

    internal val showEmptyViewLiveData=UILiveData<Boolean>()


    private var pageParams= PageParams(
        page = startPage,
        pageSize = pageSize,
        startPage = startPage
    )


    val adapterList=mutableListOf<T>()

    fun refresh(){
        pageParams.firstPage()
        loadPagedData(pageParams)
    }


    fun loadMore(){
        pageParams.nextPage()
        loadPagedData(pageParams)
    }


    protected abstract fun loadPagedData(pageParams: PageParams)

    fun pagedApiCall(block: suspend CoroutineScope.() -> AppResponse<PagedList<T>>):Job {
        val refresh=pageParams.refreshFlag
        return execute(block = {block().result},showLoading = false,
            onFailed = {
                pageParams.back()
                false
            },
            onSuccess = {pagedList->
                if(refresh){
                    adapterList.clear()
                }
                adapterList.addAll(pagedList.list)
                setEnableLoadMore(adapterList.size<pagedList.totalCount)
                showEmptyView(adapterList.isEmpty())
                notifyDataSetChanged()
            },
            onComplete = {
                if(refresh){
                    finishRefresh()
                }else{
                    finishLoadMore()
                }
            }
        )
    }


    final override fun finishRefresh() {
        finishRefreshLiveData.trigger()
    }

    final override fun finishLoadMore() {
        finishLoadMoreLiveData.trigger()
    }

    final override fun setEnableLoadMore(enableLoadMore: Boolean) {
        enableLoadMoreLiveData.changeValue(enableLoadMore)
    }

    final override fun showEmptyView(showEmpty: Boolean) {
        showEmptyViewLiveData.changeValue(showEmpty)
    }

    final override fun notifyDataSetChanged() {
        notifyDataSetLiveData.trigger()
    }


}

data class PageParams(
    var page:Int,
    val pageSize:Int,
    var startPage:Int,
    var startIndex:Int=0
){

    private var lastPage:Int=page
    private var lastStartIndex:Int=startIndex


    var refreshFlag=false

    fun back(){
        this.page=lastPage
        this.startIndex=lastStartIndex
    }

    fun firstPage(){
        this.refreshFlag=true
        this.lastPage=this.page
        this.lastStartIndex=this.startIndex
        this.page=startPage
        this.startIndex=0
    }

    fun nextPage(){
        this.refreshFlag=false
        this.lastPage=this.page
        this.lastStartIndex=this.startIndex
        this.page++
        this.startIndex+=pageSize
    }

}