package com.eju.architecture.base

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.*
import com.eju.network.ExceptionConverter
import com.eju.architecture.ResultCallback
import com.eju.architecture.livedata.CountLiveData
import com.eju.architecture.livedata.UILiveData
import com.eju.architecture.util.ReflectUtil
import com.eju.network.BaseResult
import com.eju.network.NetworkUtil
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.NullPointerException

open class BaseViewModel<M:BaseModel>():ViewModel(), IBaseView,DefaultLifecycleObserver {

    internal val exceptionLiveData=UILiveData<Exception>()

    internal val toastLiveData=UILiveData<String>()

    internal val finishPageLiveData= CountLiveData()

    internal val showLoadingLiveData= UILiveData<String>()

    internal val hideLoadingLiveData= CountLiveData()


    private val modelDelegate = lazy {
        ReflectUtil.getTypeAt<M>(javaClass,0)?.let {modelClass->
            modelClass.newInstance()
        }?:throw NullPointerException("model is null")
    }
    protected val model:M by modelDelegate

    /**
     * 启动一个协程
     */
    fun launch(block: suspend CoroutineScope.() -> Unit):Job{
        return viewModelScope.launch (block = block)
    }


    /**
     * 启动一个协程 在协程中处理返回数据
     */
    fun <T> launch(block: suspend CoroutineScope.() -> T,
                   resultCallback: ResultCallback<T>,
                   showLoading:Boolean=true,
                   loadingMsg:String?=null
    ):Job{
        return launch {
            try {
                if(showLoading){
                    showLoading(loadingMsg)
                }
                val data=block()
                resultCallback.onSuccess(data)
            } catch (e: Exception) {
                if(!resultCallback.onFailed(e)){
                    showError(e)
                }
            } finally {
                if(showLoading){
                    hideLoading()
                }
                resultCallback.onComplete()
            }
        }
    }

//    /**
//     * 启动一个协程 请求接口
//     */
//    fun <T> callApi(block: suspend CoroutineScope.() -> BaseResult<T>,
//                    resultCallback: ResultCallback<T>,
//                    showLoading:Boolean=true,
//                    loadingMsg:String?=null
//    ):Job{
//        return launch({block().result},resultCallback,showLoading,loadingMsg)
//    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        if(modelDelegate.isInitialized()){
            model.onDestroy()
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

