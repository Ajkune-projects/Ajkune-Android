package com.ajkune.professional.architecture.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Entity(tableName = "user_table")
class User {


    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var id: String = "FISI_TEST"


    @ColumnInfo(name = "token")
    var token: String = ""

    companion object {
        fun create(string: String) : User? {
            return Gson().fromJson(string, User::class.java)
        }
    }

    override fun toString(): String {
        return GsonBuilder().create().toJson(this)
    }
}