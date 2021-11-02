package com.ajkune.professional.utilities.helpers

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

object FHelper {
    fun saveImage(context: Context, bitmap: Bitmap): String {

        val rendomt = getSaltString()
        val mypath = File(createDirectory(context, "science-scope"), rendomt + ".jpg")
        var fos: FileOutputStream? = null

        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 64, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.flush()
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return mypath.path
    }

    fun createDirectory(context: Context, directoryName: String): String{
        val cw = ContextWrapper(context)
        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir(directoryName, Context.MODE_PRIVATE)
        return directory.absolutePath
    }

    fun getSaltString(): String {
        val saltchars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".lowercase()
        val salt = StringBuilder()
        val rnd = Random()
        while (salt.length < 48) { // length of the random string.
            val index = (rnd.nextFloat() * saltchars.length).toInt()
            salt.append(saltchars[index])
        }
        return salt.toString()
    }

    fun getRealPathFromURI(activity: Activity, contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity.managedQuery(contentUri, proj, null, null, null)
        val column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }
}

object Screen{
    val height = Resources.getSystem().displayMetrics.heightPixels
    val width = Resources.getSystem().displayMetrics.widthPixels

}