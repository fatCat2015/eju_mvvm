package com.eju.architecture.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.eju.architecture.livedata.CountLiveData
import com.eju.architecture.livedata.UILiveData
import com.eju.service.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

abstract class BasePagingViewModel<M:BaseRepository,T>(startPage:Int=0, pageSize:Int=10) : BaseViewModel<M>(),IPagingView{

    internal val finishRefreshLiveData= CountLiveData()

    internal val finishLoadMoreLiveData= CountLiveData()

    internal val enableLoadMoreLiveData=UILiveData<Boolean>()

    internal val notifyDataSetLiveData= CountLiveData()

    internal val showEmptyViewLiveData=UILiveData<Boolean>()

    private var enableLoadMore=false

    private var obtainnedData=false


    private var pageParams= PageParams(
        page = startPage,
        pageSize = pageSize,
        startPage = startPage
    )

    val adapterList=mutableListOf<T>()

    private var lastPagedLiveData:LiveData<PagedList<T>>?=null

    fun refresh(){
        pageParams.firstPage()
        loadPagedData()
    }


    fun loadMore(){
        if(enableLoadMore){
            pageParams.nextPage()
            loadPagedData()
        }

    }

    private fun loadPagedData(){
        obtainnedData=false
        val pagedLiveData=getPagedLiveData(pageParams)
        pagedLiveData.observeForever(pagedListObserver)
        lastPagedLiveData?.removeObserver(pagedListObserver)
        lastPagedLiveData =null
        lastPagedLiveData=pagedLiveData
    }

    private val pagedListObserver= Observer<PagedList<T>> { pagedList ->
        pagedList?.let {
            val refresh=pageParams.refreshFlag
            obtainnedData=true
            handlePagedResult(refresh,pagedList)
        }
    }

    protected abstract fun getPagedLiveData(pageParams: PageParams):LiveData<PagedList<T>>



    fun pagedFlowLiveData(onStart:((Job)->Unit)?=null, block: () -> Flow<PagedList<T>>):LiveData<PagedList<T>> {
        val refresh=pageParams.refreshFlag
        return flowLiveData (
            showLoading = false,
            onError = {
                if(!obtainnedData){
                    pageParams.back()
                }
                false
            },
            onStart = onStart,
            onComplete = {
                if(refresh){
                    finishRefresh()
                }else{
                    finishLoadMore()
                }
            },block = {
                block()
            }
        )
    }

    fun pagedLiveData(onStart:((Job)->Unit)?=null, block: suspend () -> PagedList<T>):LiveData<PagedList<T>> {
        val refresh=pageParams.refreshFlag
        return liveData (
            showLoading = false,
            onError = {
                pageParams.back()
                false
            },
            onStart = onStart,
            onComplete = {
                if(refresh){
                    finishRefresh()
                }else{
                    finishLoadMore()
                }
            },block = {
                block()
            }
        )
    }

//    fun pagedApiCall(block: suspend CoroutineScope.() -> PagedList<T>):Job {
//        val refresh=pageParams.refreshFlag
//        return launch(
//            showLoading = false,
//            onError = {
//                pageParams.back()
//                false
//            },
//            onComplete = {
//                if(refresh){
//                    finishRefresh()
//                }else{
//                    finishLoadMore()
//                }
//            },block = {
//                val pagedList=block()
//                handlePagedResult(refresh,pagedList)
//            }
//        )
//    }

    open fun handlePagedResult(refresh:Boolean,pagedList: PagedList<T>){
        if(refresh){
            adapterList.clear()
            adapterList.addAll(pagedList.list)
        }else{
            val expectedCurrentDataSize=pageParams.currentDataSize()
            val realDataSize=adapterList.size
            if(realDataSize>expectedCurrentDataSize){
                val needRemoveList= mutableListOf<T>()
                for(index in expectedCurrentDataSize until realDataSize){
                    needRemoveList.add(adapterList[index])
                }
                adapterList.removeAll(needRemoveList)
            }
            adapterList.addAll(pagedList.list)
        }
        setEnableLoadMore(verifyEnableLoadMore(pagedList).also { enableLoadMore=it })
        showEmptyView(adapterList.isEmpty())
        notifyDataSetChanged()
    }

    open fun verifyEnableLoadMore(pagedList: PagedList<T>)=adapterList.size<pagedList.totalCount

    override fun onCleared() {
        super.onCleared()
        lastPagedLiveData?.removeObserver(pagedListObserver)
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
    val startPage:Int,
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

    fun currentDataSize()=pageSize*page

}