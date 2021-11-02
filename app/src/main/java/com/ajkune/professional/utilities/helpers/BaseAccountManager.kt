package com.ajkune.professional.utilities.helpers

import android.content.Context
import javax.inject.Inject

class BaseAccountManager @Inject constructor(private val context: Context){


    private  val userIdKey = "UserId"
    private  val userTokenKey = "UserToken"

    var user : String? = null
    var token : String? = null

    fun isLogged(): Boolean {
        return true
    }
}