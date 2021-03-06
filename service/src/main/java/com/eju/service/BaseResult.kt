package com.eju.service

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseResult<T>(
    @SerializedName("err_code")
    val code:String?,
    @SerializedName("err_msg")
    val message:String?,
    val data:T?
){
    private fun isSuccess()=code=="SYS000"

    val result :T get() {
        return code?.let {
            if(isSuccess()){
                data?.let {
                    it
                }?: "" as T
            }else{
                throw ApiException(code,message)
            }
        }?:throw ApiException(ApiException.codeIsNull,"code is null")
    }
}


data class PagedList<T>(
    @SerializedName("total_count")
    val totalCount:Int,
    @SerializedName("records")
    val list:List<T>
):Serializable


