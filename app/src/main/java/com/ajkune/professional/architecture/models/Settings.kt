package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class Settings {

    @SerializedName("customer_id")
    var customerId : String = ""

    @SerializedName("merchant_id")
    var merchantId : String = ""

    var id : Int = 0
    var username: String = ""
    var password: String = ""

    companion object {
        fun create(string: String) : Settings? {
            return Gson().fromJson(string, Settings::class.java)
        }
    }
}