package com.ajkune.professional.architecture.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User {


    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var id: String = ""


    @ColumnInfo(name = "token")
    var token: String = ""
}