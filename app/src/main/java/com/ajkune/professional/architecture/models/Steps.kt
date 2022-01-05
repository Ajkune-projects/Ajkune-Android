package com.ajkune.professional.architecture.models

import com.google.gson.annotations.SerializedName

class Steps {

    @SerializedName("with_customer")
    var withCustomer : Boolean  = true

    var duration : Int = 60
}