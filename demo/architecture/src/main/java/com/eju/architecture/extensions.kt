package com.eju.architecture

import android.content.res.Resources
import android.util.TypedValue
import androidx.lifecycle.*
import com.eju.architecture.base.BasePagingViewModel
import com.eju.architecture.base.BaseViewModel
import com.eju.architecture.base.IPagingView
import com.eju.architecture.base.IView
import java.lang.StringBuilder


fun <T> LifecycleOwner.observe(liveData: LiveData<T>?,onChangedCallback:(T)->Unit){
    liveData?.observe(this, Observer<T> { t -> onChangedCallback.invoke(t) })
}

fun <T:ViewModel> ViewModelStoreOwner.getViewModel(modeClass:Class<T>, viewModelCreator:(()->T)?=null):T{
    val viewModel=getOrCreateViewModel(this,modeClass,viewModelCreator)
    observeViews(viewModel,this)
    observePagingViews(viewModel,this)
    return viewModel
}


private fun observeViews(viewModel:ViewModel,viewModelStoreOwner: ViewModelStoreOwner){
    if(viewModel is BaseViewModel && viewModelStoreOwner is LifecycleOwner && viewModelStoreOwner is IView){
        viewModelStoreOwner.observe(viewModel.exceptionLiveData){
            viewModelStoreOwner.showError(it)
        }
        viewModelStoreOwner.observe(viewModel.showLoadingLiveData){
            viewModelStoreOwner.showLoading(it)
        }
        viewModelStoreOwner.observe(viewModel.hideLoadingLiveData){
            viewModelStoreOwner.hideLoading()
        }
        viewModelStoreOwner.observe(viewModel.toastLiveData){
            viewModelStoreOwner.toast(it)
        }
        viewModelStoreOwner.observe(viewModel.finishPageLiveData){
            viewModelStoreOwner.finishPage()
        }
    }
}

private fun observePagingViews(viewModel:ViewModel,viewModelStoreOwner: ViewModelStoreOwner){
    if(viewModel is BasePagingViewModel<*> && viewModelStoreOwner is LifecycleOwner && viewModelStoreOwner is IPagingView){
        viewModelStoreOwner.observe(viewModel.finishRefreshLiveData){
            viewModelStoreOwner.finishRefresh()
        }
        viewModelStoreOwner.observe(viewModel.finishLoadMoreLiveData){
            viewModelStoreOwner.finishLoadMore()
        }
        viewModelStoreOwner.observe(viewModel.enableLoadMoreLiveData){
            viewModelStoreOwner.setEnableLoadMore(it)
        }
        viewModelStoreOwner.observe(viewModel.showEmptyViewLiveData){
            viewModelStoreOwner.showEmptyView(it)
        }
        viewModelStoreOwner.observe(viewModel.notifyDataSetLiveData){
            viewModelStoreOwner.notifyDataSetChanged()
        }
    }
}


private fun  <T: ViewModel> getOrCreateViewModel(viewModelStoreOwner: ViewModelStoreOwner, modeClass:Class<T>, viewModelCreator:(()->T)?=null):T{
    return viewModelCreator?.let {
        ViewModelProvider(viewModelStoreOwner,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return viewModelCreator.invoke() as T
            }
        }).get(modeClass)
    }?: ViewModelProvider(viewModelStoreOwner).get(modeClass)
}



