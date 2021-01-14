package com.eju.demo.viewmodel

import androidx.lifecycle.LiveData
import com.eju.architecture.base.BasePagingViewModel
import com.eju.architecture.base.BaseViewModel
import com.eju.architecture.base.PageParams
import com.eju.demo.api.Message
import com.eju.demo.model.FragmentRepository
import com.eju.service.PagedList

class FragmentViewModel:BasePagingViewModel<FragmentRepository,Message>() {


    override fun getPagedLiveData(pageParams: PageParams): LiveData<PagedList<Message>> {
        return pagedLiveData {
            model.msgList(pageParams.startIndex,pageParams.pageSize)
        }
    }


}