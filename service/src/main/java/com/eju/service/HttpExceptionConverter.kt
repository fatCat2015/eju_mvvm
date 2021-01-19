package com.eju.service

import android.net.ParseException
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface HttpExceptionConverter {
    fun convert(e:Throwable):Throwable
}

class DefaultHttpExceptionConverter: HttpExceptionConverter {

    /**
     * 转化一些异常信息为用户能读懂的信息
     */
    override fun convert(e:Throwable):Throwable{
        return convertException(e)
    }

    private fun convertException(e:Throwable):Throwable{
        val application=ServiceUtil.application
        var msg :String? =null
        when{
            e is ConnectException ->{
                msg= application.getString(R.string.ConnectException)
            }
            e is UnknownHostException ->{
                msg= application.getString(R.string.UnknownHostException)
            }
            e is IOException ->{
                msg= application.getString(R.string.UnknownHostException)
            }
            e is SocketTimeoutException ->{
                msg= application.getString(R.string.SocketTimeoutException)
            }
            e is HttpException ->{
                msg= application.getString(R.string.HttpException)
            }
            e is JsonParseException ->{
                msg= application.getString(R.string.JsonParseException)
            }
            e is ParseException ->{
                msg= application.getString(R.string.JsonParseException)
            }
            e is JSONException ->{
                msg= application.getString(R.string.JsonParseException)
            }
            e is JsonIOException ->{
                msg= application.getString(R.string.JsonParseException)
            }
        }
        return msg?.let {
            Exception(it,e)
        }?:e
    }

}