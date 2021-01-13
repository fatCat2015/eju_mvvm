import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import java.util.*
import java.util.concurrent.LinkedBlockingQueue



object ShowOneDialogHandler{

    private val dialogMap:MutableMap<FragmentManager,LinkedList<DialogFragment>> by lazy {
        mutableMapOf<FragmentManager,LinkedList<DialogFragment>>()
    }


    fun show(lifecycleOwner: LifecycleOwner,fragmentManager: FragmentManager,dialog: DialogFragment){
        val dialogList=dialogMap[fragmentManager]?:LinkedList<DialogFragment>().also {
            lifecycleOwner.lifecycle.addObserver(object:DefaultLifecycleObserver{
                override fun onDestroy(owner: LifecycleOwner) {
                    dialogMap.remove(fragmentManager)
                }
            })
            dialogMap[fragmentManager]=it
        }
        dialogList.addFirst(dialog)
        dialog.lifecycle.addObserver(dialogLifecycleObserver)
        if(dialogList.isEmpty()){
            showDialogAllowingStateLoss(fragmentManager,dialog)
        }
    }

    private val dialogLifecycleObserver=object:DefaultLifecycleObserver{
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            if(owner is DialogFragment){
                findEntryFromDialog(owner)?.let { entry->
                    val fragmentManager=entry.key
                    val dialogList=entry.value
                    dialogList.remove(owner)
                    if(!dialogList.isEmpty()){
                        val nextDialog=dialogList.last()
                        showDialogAllowingStateLoss(fragmentManager,nextDialog)
                    }
                }
            }
        }
    }

    private fun findEntryFromDialog(dialog: DialogFragment): Map.Entry<FragmentManager,LinkedList<DialogFragment>>?{
        var entry:Map.Entry<FragmentManager,LinkedList<DialogFragment>>?=null
        dialogMap.forEach {
            if(it.value.contains(dialog)){
                entry=it
                return@forEach
            }
        }
        return entry
    }


    private fun showDialogAllowingStateLoss(fragmentManager: FragmentManager,dialogFragment: DialogFragment) {
        fragmentManager.beginTransaction().add(dialogFragment, javaClass.simpleName).commitAllowingStateLoss()
    }



}