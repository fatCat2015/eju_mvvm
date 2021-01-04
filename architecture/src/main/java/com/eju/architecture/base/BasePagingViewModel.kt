package com.eju.architecture.base

import com.eju.architecture.livedata.CountLiveData
import com.eju.architecture.livedata.UILiveData
import com.eju.network.BaseResult
import com.eju.network.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

abstract class BasePagingViewModel<M:BaseModel,T>( startPage:Int=0, pageSize:Int=10) : BaseViewModel<M>(),IPagingView{

    internal val finishRefreshLiveData= CountLiveData()

    internal val finishLoadMoreLiveData= CountLiveData()

    internal val enableLoadMoreLiveData=UILiveData<Boolean>()

    internal val notifyDataSetLiveData= CountLiveData()

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

    fun pagedApiCall(block: suspend CoroutineScope.() -> PagedList<T>):Job {
        val refresh=pageParams.refreshFlag
        return launch(
            showLoading = false,
            onError = {
                pageParams.back()
                false
            },
            onComplete = {
                if(refresh){
                    finishRefresh()
                }else{
                    finishLoadMore()
                }
            },block = {
                val pagedList=block()
                handlePagedResult(refresh,pagedList)
            }
        )
    }

    open fun handlePagedResult(refresh:Boolean,pagedList: PagedList<T>){
        if(refresh){
            adapterList.clear()
        }
        adapterList.addAll(pagedList.list)
        setEnableLoadMore(verifyEnableLoadMore(pagedList))
        showEmptyView(adapterList.isEmpty())
        notifyDataSetChanged()
    }

    open fun verifyEnableLoadMore(pagedList: PagedList<T>)=adapterList.size<pagedList.totalCount


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