package com.ajkune.professional.utilities.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.provider.Settings

object AppPreferences {
    fun getShared(context: Context): SharedPreferences {
        return context.getSharedPreferences("ajkune-proffesional", Context.MODE_PRIVATE)
    }

    fun getNotificationToken(context: Context): String? {
        val sharedPreferences = AppPreferences.getShared(context)
        return sharedPreferences.getString("NotificationToken", "")
    }

    fun saveNewNotificationToken(context: Context, newToken: String){
        val sharedPreferences = AppPreferences.getShared(context)
        val editor = sharedPreferences.edit()
        editor.putString("NotificationToken", newToken)
        editor.apply()
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return  Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    val systemVersion = android.os.Build.VERSION.SDK_INT.toString()
    val deviceName  = android.os.Build.MODEL


    fun getAppVersion(context: Context): String {
        var version = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return version
    }
}