package com.ajkune.professional.architecture.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AllAppointment {

    var id : String = ""
    var type : String = ""
    var attributes : Attributes? = null

    companion object {
        fun create(string: String): AllAppointment? {
            return Gson().fromJson(string, AllAppointment::class.java)
        }

        fun createArray(string: String): List<AllAppointment>? {
            return Gson().fromJson<List<AllAppointment>>(
                string, object : TypeToken<List<AllAppointment>>() {}.type
            )
        }
    }
}