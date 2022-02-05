package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class LoginResponse {
    var token : String = ""
    var user : User? = null

    companion object {
        fun create(string: String) : LoginResponse? {
            return Gson().fromJson(string, LoginResponse::class.java)
        }
    }
}