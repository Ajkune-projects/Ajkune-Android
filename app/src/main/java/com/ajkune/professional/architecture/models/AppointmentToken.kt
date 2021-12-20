package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class AppointmentToken {

    @SerializedName("access_token")
    var accessToken : String = ""

    companion object {
        fun create(string: String): AppointmentToken? {
            return Gson().fromJson(string, AppointmentToken::class.java)
        }

        fun createArray(string: String): List<AppointmentToken>? {
            return Gson().fromJson<List<AppointmentToken>>(
                string, object : TypeToken<List<AppointmentToken>>() {}.type
            )
        }
    }
}