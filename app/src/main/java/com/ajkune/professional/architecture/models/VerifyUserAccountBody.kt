package com.ajkune.professional.architecture.models

import com.google.gson.annotations.SerializedName

class VerifyUserAccountBody {

    var name : String = ""
    var gender : String = ""
    var phone  : String = ""
    var address : String = ""
    var street : String = ""
    var country : String = ""


    @SerializedName("last_name")
    var lastName : String  = ""

    @SerializedName("date_of_birth")
    var dateOfBirth : String  = ""

    @SerializedName("zip_code")
    var zipCode : String  = ""

    @SerializedName("base64_img")
    var base64Img : String  = ""


}