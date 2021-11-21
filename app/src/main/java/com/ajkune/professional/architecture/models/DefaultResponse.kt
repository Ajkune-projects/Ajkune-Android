package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class DefaultResponse {

    var success : Boolean = false
    var message : String = ""
    var data : User? = null
    var error : Error ? = null

    companion object {
        fun create(string: String) : DefaultResponse? {
            return Gson().fromJson(string, DefaultResponse::class.java)
        }
    }

    override fun toString(): String {
        return GsonBuilder().create().toJson(this)
    }

}