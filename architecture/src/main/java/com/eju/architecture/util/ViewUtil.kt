package com.eju.architecture.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

object ViewUtil {

    fun scrollToTop(recyclerView: RecyclerView){
        var layoutManager=recyclerView.layoutManager
        when(layoutManager){
            is LinearLayoutManager -> layoutManager.scrollToPositionWithOffset(0,0)
            is GridLayoutManager -> layoutManager.scrollToPositionWithOffset(0,0)
            is StaggeredGridLayoutManager -> layoutManager.scrollToPositionWithOffset(0,0)
        }
    }

    /**
     * 不可见的view生成bitmap
     * @param view
     * @param width
     * @return
     */
    fun view2Bitmap(view: View, width: Int): Bitmap? {
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), 0)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        return view2Bitmap(view)
    }


    /**
     * 可见view生成bitmap
     * @param view
     * @return
     */
    fun view2Bitmap(view: View): Bitmap? {
        var view = view
        if (view is ScrollView) {
            view = view.getChildAt(0)
        } else if (view is NestedScrollView) {
            view = view.getChildAt(0)
        }
        val bmp =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        canvas.drawColor(Color.TRANSPARENT)
        view.draw(canvas)
        return bmp
    }


}