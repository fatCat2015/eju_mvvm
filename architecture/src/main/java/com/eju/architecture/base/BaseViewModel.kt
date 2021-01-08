package com.eju.architecture.base

import androidx.annotation.CallSuper
import androidx.lifecycle.*
import com.eju.architecture.livedata.CountLiveData
import com.eju.architecture.livedata.UILiveData
import com.eju.architecture.util.ReflectUtil
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.NullPointerException
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<M:BaseRepository>():ViewModel(), IBaseView,DefaultLifecycleObserver {

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



    protected fun createJob():Job= Job(viewModelScope.coroutineContext[Job])

    protected fun <T> liveData(showLoading: Boolean=true,
                     loadingMsg: String?=null,
                     onError:((Exception)->Boolean)? = null,
                     onComplete:(()->Unit)? = null,
                     block: suspend(Job) -> T
    ):LiveData<T>{
        val job=createJob()
        return liveData(context = job+Dispatchers.IO,timeoutInMs = 10000) {
            try {
                if(showLoading){
                    showLoading(loadingMsg)
                }
                emit(block(job))
            } catch (e: Exception) {
                if(onError?.invoke(e)!=true){
                    showError(e)
                }
            } finally {
                if(showLoading){
                    hideLoading()
                }
                onComplete?.invoke()
            }
        }
    }


    /**
     * 启动一个协程 在协程中处理返回数据
     */
    protected fun launch(showLoading:Boolean=true,
               loadingMsg:String?=null ,
               onError:((Exception)->Boolean)? = null,
               onComplete:(()->Unit)? = null,
               block: suspend CoroutineScope.() -> Unit
    ):Job{
        return viewModelScope.launch (Dispatchers.IO){
            try {
                if(showLoading){
                    showLoading(loadingMsg)
                }
                block()
            } catch (e: Exception) {
                if(onError?.invoke(e)!=true){
                    showError(e)
                }
            } finally {
                if(showLoading){
                    hideLoading()
                }
                onComplete?.invoke()
            }
        }
    }


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

