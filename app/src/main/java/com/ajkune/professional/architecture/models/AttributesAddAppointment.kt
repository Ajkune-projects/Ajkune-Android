package com.ajkune.professional.architecture.models

import com.google.gson.annotations.SerializedName

class AttributesAddAppointment {

    @SerializedName("starts_at")
    var startsAt : String  = ""

    @SerializedName("participant_count")
    var participantCount : String  = "1"

    var title : String = ""

    var steps : ArrayList<Steps> = ArrayList()

}