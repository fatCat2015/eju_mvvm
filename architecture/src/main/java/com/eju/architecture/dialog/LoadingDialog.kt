/**
 * Copyright 2019 Danny Keng
 */
package com.eju.architecture.dialog

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import com.eju.architecture.R
import com.eju.architecture.dp
import kotlinx.android.synthetic.main.dialog_loading.*

/**
 * Loading Dialog
 */
class LoadingDialog : BaseDialog(R.layout.dialog_loading) {

    companion object{
        fun newInstance(content:String):LoadingDialog{
            var loadingDialog=LoadingDialog()
            var bundle=Bundle()
            bundle.putString("content",content)
            loadingDialog.arguments=bundle
            return loadingDialog
        }
    }

    override fun setData(arguments: Bundle?) {
        var content=arguments?.getString("content")
        clLoading.background=GradientDrawable().apply {
            activity?.let { activity->
                setColor(ContextCompat.getColor(activity, R.color.colorTranslucent7))
                cornerRadius=8.dp
            }
        }
        content=if(content.isNullOrEmpty()) activity?.getString(R.string.loading) else content
        textView.visibility=if(content.isNullOrEmpty()) View.GONE else View.VISIBLE
        textView.text=content
    }

    override fun setListeners() {

    }

    override fun dimEnabled(): Boolean {
        return false
    }



}