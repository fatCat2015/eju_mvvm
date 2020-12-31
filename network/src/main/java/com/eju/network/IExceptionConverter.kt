package com.eju.network

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

interface IExceptionConverter {
    fun convert(e:Throwable?):Exception
}

class ExceptionConverter:IExceptionConverter{

    /**
     * 转化一些异常信息为用户能读懂的信息
     */
    override fun convert(e:Throwable?):Exception{
        return Exception(convertErrorMsg(e),e)
    }

    private fun convertErrorMsg(e:Throwable?):String?{
        val application=NetworkUtil.application
        var msg=e?.message
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
        return msg
    }

}