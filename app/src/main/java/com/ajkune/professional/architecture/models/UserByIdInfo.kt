package com.ajkune.professional.architecture.models

import com.google.gson.annotations.SerializedName

class UserByIdInfo {
    var id : Int = 0
    var name : String = ""
    var email : String = ""
    var phone : String = ""

    @SerializedName("last_name")
    var lastName: String = ""

    @SerializedName("profile_photo_path")
    var profilePhotoPath: String = ""

    @SerializedName("active_profile")
    var activeProfile: Int = 0

    @SerializedName("date_of_birth")
    var dateOfBirth: Int = 0


}