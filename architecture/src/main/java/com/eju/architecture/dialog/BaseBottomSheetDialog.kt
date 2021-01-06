package com.eju.architecture.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.eju.architecture.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

abstract class BaseBottomSheetDialog(private val layoutResId:Int):BottomSheetDialogFragment() {

    var dismissCallback:(()->Unit)?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setBgTransparent()
        setDialogStyle()
        setData(savedInstanceState)
        setListeners()
    }


    private fun setBgTransparent(){
        var container=view?.parent as View?
        container?.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun setDialogStyle(){
        val lp=dialog?.window?.attributes
        lp?.windowAnimations= R.style.bottomSheet_animation
        dialog?.window?.attributes=lp
    }

    protected abstract fun setData(savedInstanceState:Bundle?)

    protected abstract fun setListeners()


    open fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, UUID.randomUUID().toString())
    }

    open fun showAllowingStateLoss(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction().add(this, UUID.randomUUID().toString()).commitAllowingStateLoss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissCallback?.invoke()
    }

}