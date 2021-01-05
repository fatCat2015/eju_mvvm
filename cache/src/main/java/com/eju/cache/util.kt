package com.eju.cache

import android.R.attr.data
import java.security.MessageDigest


internal fun md5String(source:String?):String?{
    if(source.isNullOrEmpty()){
        return null
    }
    return try {
        val byteArray=source.toByteArray()
        val msgDigest=MessageDigest.getInstance("MD5")
        msgDigest.update(byteArray)
        val digestedByteArray=msgDigest.digest()
        val sb = StringBuilder()
        for (b in digestedByteArray) {
            sb.append(String.format("%02x", b))
        }
        sb.toString()
    } catch (e: Exception) {
        null
    }
}