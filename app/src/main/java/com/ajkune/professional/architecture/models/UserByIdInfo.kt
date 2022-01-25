package com.ajkune.professional.architecture.models

import com.google.gson.annotations.SerializedName

class UserByIdInfo {
    var id : Int = 0
    var name : String = ""
    var email : String = ""
    var phone : String = ""
    var street : String = ""
    var address : String = ""
    var country : String = ""


    @SerializedName("last_name")
    var lastName: String = ""

    @SerializedName("profile_photo_path")
    var profilePhotoPath: String = ""

    @SerializedName("active_profile")
    var activeProfile: Int = 0

    @SerializedName("gender")
    var  gender: String? = null

    @SerializedName("date_of_birth")
    var dateOfBirth: String? = null

    @SerializedName("image_name")
    var imageName: String? = null

    @SerializedName("zip_code")
    var zipCode: Int = 0


}