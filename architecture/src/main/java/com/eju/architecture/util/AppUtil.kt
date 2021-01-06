package com.eju.architecture.util

import android.app.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.eju.architecture.R


object AppUtil {

    fun openDial(context: Context,phone:String?){
        phone?.let {
            var intent=Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            if(canResolveActivity(context,intent)){
                context.startActivity(intent)
            }
        }
    }

    fun copyText(context: Context,text:String?){
        text?.let {
            var clipboardManager=context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Label",text))
        }
    }

    /**
     * 是否允许安装未知来源的应用
     */
    fun canRequestPackageInstalls(context: Context):Boolean{
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return true
        }
        return context.packageManager.canRequestPackageInstalls()
    }
    /**
     * 打开设置是否允许安装位置来源应用设置
     */
    fun openPackageInstallsSettings(activity: Activity,requestCode:Int) {
        val packageURI = Uri.parse("package:${activity.packageName}")
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
        if (canResolveActivity(activity, intent)) {
            activity.startActivityForResult(intent, requestCode)
        }
    }
    fun installApk(context: Context, apkFile: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(FileUtil.getFileUri(context, apkFile), "application/vnd.android.package-archive")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
        if(canResolveActivity(context,intent)){
            context.startActivity(intent)
        }

    }

//    fun showProgressNotification(context: Context,title:String,progress:Int,smallIconResId:Int,notificationId:Int){
//        var notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        var notification:Notification?=null
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            var channel=NotificationChannel("${context.packageName}.channel","2222",NotificationManager.IMPORTANCE_MIN)
//            notificationManager.createNotificationChannel(channel)
//            notification=Notification.Builder(context,context.packageName)
//                .setContentTitle(title)
//                .setContentText("${progress}%")
//                .setProgress(100,progress,false)
//                .setAutoCancel(false)
//                .setSmallIcon(smallIconResId)
////                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
//                .build()
//        }else{
//            notification= NotificationCompat.Builder(context,"${context.packageName}.channel")
//                .setContentTitle(title)
//                .setContentText("${progress}%")
//                .setProgress(100,progress,false)
//                .setAutoCancel(false)
//                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
//                .setSmallIcon(smallIconResId)
////                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
//                .build()
//        }
//        notificationManager.notify(notificationId,notification)
//    }
//
//    fun cancelNotification(context: Context,notificationId:Int){
//        var notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.cancel(notificationId)
//    }


    fun canResolveActivity(context: Context,intent: Intent):Boolean{
        return intent.resolveActivity(context.packageManager)!=null
    }



    /**
     * 检测应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    fun appIsExist(context: Context, packageName: String): Boolean {
        var packageInfo: PackageInfo?
        try {
            packageInfo = context.packageManager.getPackageInfo(packageName, 0)
        } catch (e: java.lang.Exception) {
            packageInfo = null
        }
        return packageInfo != null
    }

}