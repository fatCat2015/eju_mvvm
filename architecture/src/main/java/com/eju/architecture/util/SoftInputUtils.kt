package com.eju.architecture.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object SoftInputUtils {


    fun isShouldHideSoftInput(focusView: View?, event: MotionEvent): Boolean {
        if (focusView is EditText) {
            val positions = IntArray(2)
            focusView.getLocationOnScreen(positions)
            val left = positions[0]
            val top = positions[1]
            val right = left + focusView.width
            val bottom = top + focusView.height
            val rec = Rect(left, top, right, bottom)
            return !rec.contains(event.rawX.toInt(), event.rawY.toInt())
        }
        return false
    }


    fun hideSoftInput(token: IBinder?, activity: Activity) {
        if (token != null) {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


    fun showSoftInput(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }


}
