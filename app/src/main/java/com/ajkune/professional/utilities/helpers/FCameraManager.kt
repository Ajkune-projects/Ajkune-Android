package com.ajkune.professional.utilities.helpers

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

object FCameraManager {

    fun openDefaultCamera(frg: androidx.fragment.app.Fragment, cameraRequestCode: Int): Uri {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
        val capturedImageUri =
            frg.requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(frg.requireContext().packageManager) != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri)
            frg.startActivityForResult(cameraIntent, cameraRequestCode)
        }

        return capturedImageUri!!
    }
}