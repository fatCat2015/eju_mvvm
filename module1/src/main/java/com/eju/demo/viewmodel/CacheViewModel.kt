package com.eju.demo.viewmodel

import androidx.lifecycle.LiveData
import com.eju.architecture.base.BaseViewModel
import com.eju.demo.api.HelpDetail
import com.eju.demo.model.CacheRepository

class CacheViewModel:BaseViewModel<CacheRepository>() {

    val data0:LiveData<HelpDetail>
        get(){
            return liveData {
                model.useCacheIfRemoteFailed()
            }
        }

    val data1:LiveData<HelpDetail>
        get(){
            return liveData {
                model.useCacheIfExists()
            }
        }

    val data2:LiveData<HelpDetail>
        get(){
            return flowLiveData {
                model.firstCacheThenRemote()
            }
        }
}