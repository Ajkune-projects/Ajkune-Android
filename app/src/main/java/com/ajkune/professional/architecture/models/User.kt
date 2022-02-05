package com.ajkune.professional.architecture.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
class User {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "email")
    var email: String? = null

    @ColumnInfo(name = "active_profile")
    @SerializedName("active_profile")
    var activeProfile: Int? = null

    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    var lastName: String? = null

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