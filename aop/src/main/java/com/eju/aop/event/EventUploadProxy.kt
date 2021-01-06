package com.eju.aop.event

import org.json.JSONObject

object EventUploadProxy : IEventUpload {

    private var eventUpload: IEventUpload? = null

    @Synchronized
    fun initEventUpload(eventUpload: IEventUpload) {
        this.eventUpload = eventUpload
    }

    override fun uploadEvent(eventCode:String,event:String,key:String) {
        eventUpload?.uploadEvent(eventCode, event,key)
    }
}