package com.ajkune.professional.architecture.models

import com.google.gson.annotations.SerializedName

class AddressResponse {
    var line1 : String = ""
    var line2 : String = ""
    var city : String = ""
    var state : String = ""

    @SerializedName("postal_code")
    var postalCode : String = ""
}