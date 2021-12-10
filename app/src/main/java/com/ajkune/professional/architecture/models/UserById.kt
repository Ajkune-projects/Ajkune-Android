package com.ajkune.professional.architecture.models

import com.google.gson.Gson

class UserById {
    var user : UserByIdInfo? = null

    companion object {
        fun create(string: String) : UserById? {
            return Gson().fromJson(string, UserById::class.java)
        }
    }
}