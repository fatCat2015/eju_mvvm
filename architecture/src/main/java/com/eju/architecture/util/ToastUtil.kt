package com.eju.architecture.util

import android.widget.Toast
import com.eju.architecture.application

object ToastUtil {

    fun toast(msg:String?,shortFlag:Boolean=true){
        if(!msg.isNullOrEmpty()){
            Toast.makeText(application, msg, if(shortFlag) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
        }

    }
}