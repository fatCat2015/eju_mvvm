package com.eju.network

import java.lang.RuntimeException

open class ApiException(val code:String?,val msg:String?):RuntimeException(msg) {


    companion object{
        const val codeIsNull = "-1"
        const val dataIsNull = "-2"
    }

}

class CodeNullException():ApiException(codeIsNull,"code为null")
class DataNullException():ApiException(dataIsNull,"data为null")