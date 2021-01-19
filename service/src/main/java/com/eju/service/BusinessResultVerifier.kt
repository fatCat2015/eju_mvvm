package com.eju.service

import org.json.JSONObject

interface BusinessResultVerifier{
    fun hasErrorCode(responseJSONObject: JSONObject):Boolean
    fun getErrorCode(responseJSONObject: JSONObject):String
    fun businessIsSuccess(errorCode:String):Boolean
    fun getErrorMsg(responseJSONObject: JSONObject):String
}

class DefaultBusinessResultVerifier:BusinessResultVerifier{

    override fun hasErrorCode(responseJSONObject: JSONObject): Boolean {
        return responseJSONObject.has("err_code")
    }

    override fun getErrorCode(responseJSONObject: JSONObject): String {
        return responseJSONObject.optString("err_code")
    }

    override fun businessIsSuccess(errorCode: String): Boolean {
        return errorCode=="SYS000"
    }

    override fun getErrorMsg(responseJSONObject: JSONObject): String {
        return responseJSONObject.optString("err_msg")
    }

}
