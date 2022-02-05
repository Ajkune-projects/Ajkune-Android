package com.ajkune.professional.architecture.models

import com.google.gson.Gson

class HasUserGiftsResponse {
    var message : String = ""
    var success : String = ""
    var userHasGift : Boolean = false

    companion object {
        fun create(string: String) : HasUserGiftsResponse? {
            return Gson().fromJson(string, HasUserGiftsResponse::class.java)
        }
    }
}