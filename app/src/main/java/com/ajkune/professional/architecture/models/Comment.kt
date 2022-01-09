package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class Comment {

    var id : Int = 0
    var title : String = ""
    var comment : String = ""
    var user : UserByIdInfo? = null

    companion object {
        fun create(string: String): Comment? {
            return Gson().fromJson(string, Comment::class.java)
        }
    }

}