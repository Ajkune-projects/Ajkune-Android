package com.ajkune.professional.architecture.fragment.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.models.Address
import com.ajkune.professional.architecture.models.User
import com.ajkune.professional.architecture.models.UserByIdInfo
import com.ajkune.professional.architecture.models.VerifyUserAccountBody
import com.ajkune.professional.architecture.viewmodels.dashboard.MyProfileViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.MyProfileFragmentBinding
import com.ajkune.professional.utilities.extensions.hideKeyboard
import com.ajkune.professional.utilities.extensions.isEmail
import com.ajkune.professional.utilities.extensions.loadUrl
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import com.ajkune.professional.utilities.helpers.FCameraManager
import com.ajkune.professional.utilities.helpers.FHelper
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.my_profile_fragment.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MyProfileFragment : BaseFragment() {

    lateinit var binding : MyProfileFragmentBinding

    var bitmap: Bitmap? = null
    var iconBytes = ""

    private var galleryRequestCode = 100

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    @Inject
    lateinit var baseAccountManager: BaseAccountManager

    var user : UserByIdInfo = UserByIdInfo()

    var isFromUserPhoto : Boolean = false

    private var cameraRequestCode = 1001
    private val requestCodePermissions = 1003
    private val requestCodePermissionsCamera = 1004

    private var capturedImageUri: Uri? = null

    private var date: DatePickerDialog.OnDateSetListener? = null
    private var fDate: String? = null
    private var calendar: Calendar = Calendar.getInstance()

    var imagePathGallery: String = ""

    var street = ""
    var city = ""
    var zipCode = ""
    var country = ""

    companion object {
        fun newInstance() = MyProfileFragment()
    }

    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.GONE
        binding = DataBindingUtil.inflate(inflater, R.layout.my_profile_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MyProfileViewModel::class.java]
        initBaseFunctions()
        getDate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == cameraRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                iconBytes = ""
                imagePathGallery = ""
                bitmap = null
                imagePathGallery = FHelper.getRealPathFromURI(requireActivity(), capturedImageUri!!)
                bitmap = BitmapFactory.decodeFile(imagePathGallery)
                val rotatedBitmap = rotateBitmap(bitmap!!, getOrientationForImage(imagePathGallery))
                bitMapToString(rotatedBitmap!!)
                iconBytes = bitMapToString(rotatedBitmap)
                binding.imgUserProfile.loadUrl(imagePathGallery)
            }

        }

        if (requestCode == galleryRequestCode && data != null) {
            iconBytes = ""
            imagePathGallery = ""
            bitmap = null
            val image = ImagePicker.getFirstImageOrNull(data)
            imagePathGallery = image.path
            bitmap = BitmapFactory.decodeFile(imagePathGallery)
            bitMapToString(bitmap!!)
            val rotatedBitmap = rotateBitmap(bitmap!!, getOrientationForImage(imagePathGallery))
            bitMapToString(rotatedBitmap!!)
            iconBytes = bitMapToString(rotatedBitmap)
            binding.imgUserProfile.loadUrl(imagePathGallery)
        }

    }

    override fun onResume() {
        if (!isFromUserPhoto){
            showLoader()
            val currentAddress = baseAccountManager.currentUserAddress
            if (currentAddress != null){
                val gson = Gson()
                val obj: Address = gson.fromJson(currentAddress, Address::class.java)
                val userVerified = VerifyUserAccountBody()
                userVerified.name = binding.etFirstName.text.toString()
                userVerified.lastName = binding.etLastName.text.toString()
                userVerified.gender = binding.etGender.text.toString()
                userVerified.dateOfBirth = binding.etBirthDate.text.toString()
                userVerified.phone = binding.etPhoneNumber.text.toString()
                userVerified.address = obj.city
                userVerified.street = obj.street
                userVerified.zipCode = obj.zipCode
                userVerified.country = obj.country
                userVerified.base64Img = iconBytes
                viewModel.verifyUserProfileV2(userVerified)
            }else{
                viewModel.getUserById()
            }


        }
        super.onResume()
    }

    override fun onLoad() {

        viewModel.userById.observe(this, Observer {
            hideLoader()
            user = it.user!!
            binding.etEmail.setText(user.email)
            binding.etFirstName.setText(user.name)
            binding.etLastName.setText(user.lastName)


            if (user.lastName != null){
                binding.txtUserName.text = user.name + user.lastName
            }else{
                binding.txtUserName.text = user.name
            }

            binding.etGender.setText(user.gender)
            binding.etBirthDate.setText(user.dateOfBirth)
            binding.etPhoneNumber.setText(user.phone)
            it.user!!.profilePhotoPath?.let {
                binding.imgUserProfile.loadUrl(it)
            }


            if (it.user?.activeProfile == 0){
                binding.txtUserName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                binding.clImgUserProfile.setBackgroundResource(0)
                binding.txtAddNewAddress.visibility = View.VISIBLE
                binding.txtEditAddress.visibility = View.INVISIBLE

                val currentAddress = baseAccountManager.currentUserAddress
                if (currentAddress != null){
                    val gson = Gson()
                    val obj: Address = gson.fromJson(currentAddress, Address::class.java)
                    binding.txtEditAddress.visibility = View.VISIBLE
                    binding.txtAddNewAddress.visibility = View.GONE
                    binding.txtNoAddressYet.visibility = View.GONE

                    street =  obj.street
                    city = obj.city
                    zipCode = obj.zipCode
                    country = obj.country

                    binding.txtUserAddress.text = getString(R.string.user_address, obj.street, obj.city, obj.zipCode, obj.country)
                }
            }else{
                binding.clImgUserProfile.setBackgroundResource(R.drawable.circle_user_verified)
                binding.txtUserName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.verified_use_icon, 0);
                binding.txtEditAddress.visibility = View.VISIBLE
                binding.txtAddNewAddress.visibility = View.GONE
                binding.txtNoAddressYet.visibility = View.GONE

                street = user.street
                city = user.address
                zipCode = user.zipCode.toString()
                country = user.country

                binding.txtUserAddress.text = getString(R.string.user_address, street, city, zipCode, country)
            }
        })

        viewModel.successVerifyProfile.observe(this, Observer {
            if (it != null) {
                hideLoader()
                viewModel.getUserById()
            }
        })
    }

    override fun onError() {
        viewModel.errorV2.observe(this, Observer {
            if (it != null) {
                hideLoader()
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.error.observe(this, Observer {
            if (it != null) {
                hideLoader()
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClickEvents() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etGender.setOnClickListener {
            binding.cvGender.visibility = View.VISIBLE
        }

        binding.clMain.setOnClickListener {
            binding.cvGender.visibility = View.GONE
        }

        binding.txtFemale.setOnClickListener {
            binding.cvGender.visibility = View.GONE
            binding.etGender.setText(resources.getString(R.string.female))
        }
        binding.txtMale.setOnClickListener {
            binding.cvGender.visibility = View.GONE
            binding.etGender.setText(resources.getString(R.string.male))
        }
        binding.txtOther.setOnClickListener {
            binding.cvGender.visibility = View.GONE
            binding.etGender.setText(resources.getString(R.string.other))
        }

        binding.etBirthDate.setOnClickListener{
            setDate()
        }

        binding.txtAddNewAddress.setOnClickListener {
            isFromUserPhoto = false
            findNavController().navigate(MyProfileFragmentDirections.actionMyProfileFragmentToAddAddressFragment("", "",0, ""))
        }

        binding.txtEditAddress.setOnClickListener {
            isFromUserPhoto = false
            findNavController().navigate(MyProfileFragmentDirections.actionMyProfileFragmentToAddAddressFragment(street, city, zipCode.toInt(), country))
        }

        binding.clImgUserProfile.setOnClickListener {
            showProfileImageAlert()
        }

        binding.btnSave.setOnClickListener {
            if (validateFields()){
                showLoader()
                val userVerified = VerifyUserAccountBody()
                userVerified.name = binding.etFirstName.text.toString()
                userVerified.lastName = binding.etLastName.text.toString()
                userVerified.gender = binding.etGender.text.toString()
                userVerified.dateOfBirth = binding.etBirthDate.text.toString()
                userVerified.phone = binding.etPhoneNumber.text.toString()
                userVerified.address = city
                userVerified.street = street
                userVerified.zipCode = zipCode
                userVerified.country = country
                userVerified.base64Img = iconBytes
                viewModel.verifyUserProfileV2(userVerified)
            }

        }
    }

    private fun validateFields() : Boolean {
        var warningMessage = ""
        if (binding.etFirstName.text.toString().isEmpty()) {
            warningMessage = "Please fill all requirements Fields"
        } else if (binding.etLastName.text.toString().isEmpty()) {
            warningMessage = "Please fill all requirements Fields"
        }
        else if (binding.etGender.text.toString().isEmpty()) {
            warningMessage = "Please fill all requirements Fields"
            }
        else if (binding.etBirthDate.text.toString().isEmpty()){
            warningMessage = "Please fill all requirements Fields"
        }else if (binding.etPhoneNumber.text.toString().isEmpty()){
            warningMessage = "Please fill all requirements Fields"
        }else if (binding.txtUserAddress.text.toString().isEmpty()) {
            warningMessage = "Please add your address"
        }
        if (warningMessage.isNotEmpty()) {
            Toast.makeText(context, warningMessage, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun setDate() {
        val date = DatePickerDialog(
            requireContext(), date, calendar.get(Calendar.YEAR), calendar.get(
                Calendar.MONTH
            ),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        date.datePicker.maxDate = Calendar.getInstance().timeInMillis
        date.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate() {
        date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            fDate = SimpleDateFormat("dd-MM-yyyy").format(calendar.time)
            binding.etBirthDate.setText(fDate)
        }
    }

    override fun setToolbar() {
    }

    private fun showProfileImageAlert() {
        val alert = AlertDialog.Builder(context)
            .setItems(
                arrayOf(
                    getString(R.string.take_photo),
                    getString(R.string.choose_from_gallery)
                )
            ) { _, which ->
                when (which) {
                    0 -> checkForPermissionsCamera()
                    1 -> checkForPermissionsGallery()
                }
            }
        alert.setCancelable(true)
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
        alert.setOnCancelListener { it.dismiss() }
        alert.create()
        alert.show()
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
            isFromUserPhoto = true
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
            isFromUserPhoto = true
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

    fun getOrientationForImage(path: String): Int {
        var exif: androidx.exifinterface.media.ExifInterface? = null
        try {
            exif = androidx.exifinterface.media.ExifInterface(path)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return exif!!.getAttributeInt(
            androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION,
            androidx.exifinterface.media.ExifInterface.ORIENTATION_UNDEFINED
        )
    }
}