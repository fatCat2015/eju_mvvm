package com.eju.network

import com.google.gson.annotations.SerializedName

data class AppResponse<T>(
    @SerializedName("err_code")
    val code:String?,
    @SerializedName("err_msg")
    val message:String?,
    val data:T?
){
    fun isSuccess()=code=="SYS000"

    val result :T get() {
        return code?.let {
            if(isSuccess()){
                data?.let {
                    it
                }?: throw DataNullException()
            }else{
                throw ApiException(code,message)
            }
        }?:throw CodeNullException()
    }
}



data class PagedList<T>(
    val totalCount:Int,
    val list:List<T>
)


