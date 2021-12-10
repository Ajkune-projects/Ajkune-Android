package com.ajkune.professional.architecture.fragment.dashboard

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ajkune.professional.R
import com.ajkune.professional.architecture.models.User
import com.ajkune.professional.architecture.models.UserByIdInfo
import com.ajkune.professional.architecture.viewmodels.dashboard.MyProfileViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.MyProfileFragmentBinding
import com.ajkune.professional.utilities.helpers.FCameraManager
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class MyProfileFragment : BaseFragment() {

    lateinit var binding : MyProfileFragmentBinding

    var bitmap: Bitmap? = null
    var iconBytes = ""

    private var galleryRequestCode = 100

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    var user : UserByIdInfo? = null

    private var cameraRequestCode = 1001
    private val requestCodePermissions = 1003
    private val requestCodePermissionsCamera = 1004

    private var capturedImageUri: Uri? = null

    companion object {
        fun newInstance() = MyProfileFragment()
    }

    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_profile_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MyProfileViewModel::class.java]
        initBaseFunctions()
    }

    override fun onLoad() {

        viewModel.getUserById()

        viewModel.userById.observe(this, Observer {
            user = it.user
            binding.etEmail.setText(user?.email)
            binding.etFirstName.setText(user?.name)
        })
    }

    override fun onError() {
    }

    override fun onClickEvents() {
    }

    override fun setToolbar() {
    }

    fun selectImageInAlbum() {
        ImagePicker.create(this)
            .returnMode(ReturnMode.ALL)
            .folderMode(true)
            .single()
            .showCamera(false)
            .imageDirectory("Camera")
            .imageFullDirectory(Environment.getExternalStorageDirectory().path)
            .start(galleryRequestCode)
    }

    fun bitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    @AfterPermissionGranted(1004)
    fun checkForPermissionsCamera() {
        if (EasyPermissions.hasPermissions(
                requireContext(),
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            takeNewPhoto()
        } else {
            EasyPermissions.requestPermissions(
                this,
                resources.getString(R.string.ajkune_camera_permission),
                requestCodePermissionsCamera,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    @AfterPermissionGranted(1003)
    fun checkForPermissionsGallery() {
        if (EasyPermissions.hasPermissions(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            selectImageInAlbum()
        } else {
            EasyPermissions.requestPermissions(
                this, resources.getString(R.string.ajkune_gallery_permission),
                requestCodePermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun takeNewPhoto() {
        capturedImageUri = FCameraManager.openDefaultCamera(this, cameraRequestCode)
    }

    fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap? {
        val matrix = Matrix()
        when (orientation) {
            androidx.exifinterface.media.ExifInterface.ORIENTATION_NORMAL -> return bitmap
            androidx.exifinterface.media.ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(
                -1f,
                1f
            )
            androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(
                180f
            )
            androidx.exifinterface.media.ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            androidx.exifinterface.media.ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            androidx.exifinterface.media.ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }
        return try {
            val bmRotated =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            //bitmap.recycle()
            bmRotated
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            null
        }
    }
}