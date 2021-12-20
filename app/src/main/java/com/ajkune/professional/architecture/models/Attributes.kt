package com.ajkune.professional.architecture.models

import com.google.gson.annotations.SerializedName

class Attributes {

    @SerializedName("starts_at")
    var startsAt : String = ""

    @SerializedName("ends_at")
    var endsAt : String = ""
}