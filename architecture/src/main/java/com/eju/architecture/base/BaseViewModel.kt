package com.eju.architecture.base

import androidx.lifecycle.*
import com.eju.architecture.livedata.CountLiveData
import com.eju.architecture.livedata.UILiveData
import com.eju.network.AppResponse
import kotlinx.coroutines.*
import java.lang.Exception

open class BaseViewModel():ViewModel(), IView {

    internal val exceptionLiveData=UILiveData<Exception>()

    internal val toastLiveData=UILiveData<String>()

    internal val finishPageLiveData=
        CountLiveData()

    internal val showLoadingLiveData=
        UILiveData<String>()

    internal val hideLoadingLiveData=
        CountLiveData()



    fun launch(block: suspend CoroutineScope.() -> Unit):Job{
        return viewModelScope.launch (block = block)
    }


    fun <T> execute(block: suspend CoroutineScope.() -> T,
                    showLoading:Boolean=true,
                    loadingMsg:String?=null,
                    onFailed: ((Exception) -> Boolean)? = null,
                    onComplete: (() -> Unit)? = null,
                    onSuccess: (T) -> Unit
    ):Job{
        return launch {
            try {
                if(showLoading){
                    showLoading(loadingMsg)
                }
                val data=block()
                onSuccess?.invoke(data)
            } catch (e: Exception) {
                onFailed?.let {
                    if(!onFailed.invoke(e)){
                        showError(e)
                    }
                }?:showError(e)

            } finally {
                if(showLoading){
                    hideLoading()
                }
                onComplete?.invoke()
            }
        }
    }


    fun <T> apiCall(block: suspend CoroutineScope.() -> AppResponse<T>,
                    showLoading:Boolean=true,
                    loadingMsg:String?=null,
                    onFailed: ((Exception) -> Boolean)? = null,
                    onComplete: (() -> Unit)? = null,
                    onSuccess: (T) -> Unit
    ):Job {
        return execute(block = {block().result},showLoading = showLoading,loadingMsg = loadingMsg,
            onFailed = onFailed,onComplete = onComplete,onSuccess = onSuccess
        )
    }



    fun <T> liveData(block: suspend LiveDataScope<T>.() -> Unit):LiveData<T>{
        return liveData(context = viewModelScope.coroutineContext,timeoutInMs =10000,block = block)
    }

    fun <T> createLiveData(
        showLoading:Boolean=true,
        loadingMsg:String?=null,
        onFailed: ((Exception) -> Boolean)? = null,
        onComplete: (() -> Unit)? = null,
        block: suspend CoroutineScope.() -> T,
    ):LiveData<T>{
        return liveData {
            try {
                if(showLoading){
                    showLoading(loadingMsg)
                }
                withContext(viewModelScope.coroutineContext){
                    emit(block())
                }
            } catch (e: Exception) {
                onFailed?.let {
                    if(!onFailed.invoke(e)){
                        showError(e)
                    }
                }?:showError(e)

            } finally {
                if(showLoading){
                    hideLoading()
                }
                onComplete?.invoke()
            }

        }
    }

    fun <T> createApiLiveData(
        showLoading:Boolean=true,
        loadingMsg:String?=null,
        onFailed: ((Exception) -> Boolean)? = null,
        onComplete: (() -> Unit)? = null,
        block: suspend CoroutineScope.() -> AppResponse<T>,
    ):LiveData<T>{
        return createLiveData(showLoading=showLoading,loadingMsg = loadingMsg,onFailed = onFailed,onComplete = onComplete){
            block().result
        }
    }



    final override fun showLoading(msg: String?) {
        showLoadingLiveData.changeValue(msg)
    }

    final override fun hideLoading() {
        hideLoadingLiveData.trigger()
    }

    final override fun toast(msg: String?) {
        toastLiveData.changeValue(msg)
    }

    final override fun showError(exception: Exception?) {
        exceptionLiveData.changeValue(exception)
    }

    final override fun finishPage() {
        finishPageLiveData.trigger()
    }


}

