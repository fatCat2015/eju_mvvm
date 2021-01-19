package com.eju.architecture.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.widget.ScrollView
import androidx.core.util.Consumer
import androidx.core.widget.NestedScrollView
import java.io.ByteArrayOutputStream

object BitmapUtils {

    fun bmp2ByteArray(bmp: Bitmap, needRecycle: Boolean=true): ByteArray {
        return ByteArrayOutputStream().use {outputStream ->
            bmp.compress(CompressFormat.JPEG, 100, outputStream)
            if (needRecycle) {
                bmp.recycle()
            }
            outputStream.toByteArray()
        }
    }


}