package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GiftAddedResponse {
    var gift : Gift? = null

    companion object {
        fun create(string: String): GiftAddedResponse? {
            return Gson().fromJson(string, GiftAddedResponse::class.java)
        }

        fun createArray(string: String): List<GiftAddedResponse>? {
            return Gson().fromJson<List<GiftAddedResponse>>(
                string, object : TypeToken<List<GiftAddedResponse>>() {}.type
            )
        }
    }
}