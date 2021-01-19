package com.eju.architecture.util

import java.lang.reflect.ParameterizedType

object ReflectUtil {

    /**
     * 获取targetClass类第index个泛型的实际类型
     */
    fun <T> getTypeAt(targetClass:Class<*>,index:Int):Class<T>?{
        val typeArray = (targetClass.genericSuperclass as? ParameterizedType?)?.actualTypeArguments
        return typeArray?.let {typeArray->
            if(index in typeArray.indices){
                typeArray[index] as? Class<T>?
            }else{
                null
            }
        }?:null
    }



}