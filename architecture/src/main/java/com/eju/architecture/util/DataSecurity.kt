package com.eju.architecture.util

import android.text.TextUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object DataSecurity {

    fun md5(string: String): String? {
        if (TextUtils.isEmpty(string)) {
            return ""
        }
        var md5: MessageDigest? = null
        try {
            md5 = MessageDigest.getInstance("MD5")
            val bytes = md5.digest(string.toByteArray())
            var result: String? = ""
            for (b in bytes) {
                var temp = Integer.toHexString(b.toInt() and 0xff)
                if (temp.length == 1) {
                    temp = "0$temp"
                }
                result += temp
            }
            return result
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}