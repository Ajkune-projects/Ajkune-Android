package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class Gift {
    var id : Int = 1
    var title : String = ""
    var description : String = ""
    var status : String = ""

    @SerializedName("image_url")
    var imageUrl: String? = null

    companion object {
        fun create(string: String): Gift? {
            return Gson().fromJson(string, Gift::class.java)
        }

        fun createArray(string: String): List<Gift>? {
            return Gson().fromJson<List<Gift>>(
                string, object : TypeToken<List<Gift>>() {}.type
            )
        }
    }
}