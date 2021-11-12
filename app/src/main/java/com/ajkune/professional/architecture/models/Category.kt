package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class Category {

    var id : Int = 0
    var name : String = ""
    var status : String = ""


    companion object {
        fun create(string: String): Category? {
            return Gson().fromJson(string, Category::class.java)
        }

        fun createArray(string: String): List<Category>? {
            return Gson().fromJson<List<Category>>(
                string, object : TypeToken<List<Category>>() {}.type
            )
        }
    }
}