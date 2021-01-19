package com.eju.architecture.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.eju.architecture.screenWidth
import java.util.*


abstract class BaseDialog(private val layoutResId:Int) : DialogFragment() {

    var dismissCallback:(()->Unit)?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(cancelable())
        dialog?.setCanceledOnTouchOutside(cancelableOnTouchOutside())
        return inflater.inflate(layoutResId,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setData(savedInstanceState)
        setListeners()
    }

    protected abstract fun setData(savedInstanceState: Bundle?)

    protected abstract fun setListeners()


    override fun onStart() {
        super.onStart()
        setDialog()
    }

    private fun setDialog() {
        dialog?.window?.let {window ->
            val lp = window.attributes
            lp.height = if(height()==0) WindowManager.LayoutParams.WRAP_CONTENT else height()
            val widthRadio = widthRatio()
            lp.width = if (widthRadio == 0f) WindowManager.LayoutParams.WRAP_CONTENT else (screenWidth * widthRadio).toInt()
            if (windowAnimations() != 0) {
                window.setWindowAnimations(windowAnimations())
            }
            if (!dimEnabled()) {
                window.setDimAmount(0f)
            }else{
                window.setDimAmount(0.35F)
            }
            window.setGravity(gravity())
            window.attributes = lp
        }
    }

    open fun cancelable()=true

    open fun cancelableOnTouchOutside()=true

    open fun widthRatio(): Float {
        return 0f
    }

    open fun height():Int{
        return 0
    }

    open fun gravity(): Int {
        return Gravity.CENTER
    }

    open fun windowAnimations(): Int {
        return 0
    }


    open fun dimEnabled(): Boolean {
        return true
    }


    open fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, UUID.randomUUID().toString())
    }

    open fun showAllowingStateLoss(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction().add(this,UUID.randomUUID().toString()).commitAllowingStateLoss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissCallback?.invoke()
    }

}
