package com.ajkune.professional.rest

import android.util.Log
import com.ajkune.professional.architecture.models.DefaultResponse
import com.ajkune.professional.architecture.models.LoginResponse
import com.ajkune.professional.architecture.models.User
import com.ajkune.professional.di.anotation.ForServiceRest
import com.ajkune.professional.di.anotation.ForUserRest
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import java.lang.Exception
import javax.inject.Inject

@ForUserRest
class UserRest @Inject constructor(@ForServiceRest private var serviceREST: ServiceRest, private var baseAccountManager: BaseAccountManager) {


    fun login(userName: String, password: String, completion: (LoginResponse?, Exception?) -> Unit){

        val params = mapOf("email" to userName, "password" to password)

        val request = HttpRequest("login", params, HttpRequestMethod.POST,false)

        serviceREST.request(request,""){response ->
            if (response.isHttpSuccess()){
                val account : LoginResponse?
                try {
                    account = LoginResponse.create(response.getDataString())
                    completion(account, null)
                    Log.i("user", account.toString())
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun register(firstName : String, lastName: String, email: String, password: String, completion: (DefaultResponse?, Exception?) -> Unit){

        val params = mapOf("name" to firstName ,"email" to email,"password" to password)

        val request = HttpRequest("register", params, HttpRequestMethod.POST, false)

        serviceREST.request(request,""){response ->
            if (response.isHttpSuccess()){
                val defaultResponse : DefaultResponse?
                try {
                    defaultResponse = DefaultResponse.create(response.getDataString())
                    completion(defaultResponse, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun forgotPassword(email : String, completion: (DefaultResponse?, Exception?) -> Unit){

        val params = mapOf("email" to email)

        val request = HttpRequest("forgotEmail", params, HttpRequestMethod.POST, false)

        serviceREST.request(request,""){response ->
            if (response.isHttpSuccess()){
                val defaultResponse : DefaultResponse?
                try {
                    defaultResponse = DefaultResponse.create(response.getDataString())
                    completion(defaultResponse, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun checkResetPasswordCode(finalCode: String, completion: (DefaultResponse?, Exception?) -> Unit){

        val params = mapOf("code" to finalCode)

        val request = HttpRequest("forgotEmail/code", params, HttpRequestMethod.POST,false)

        serviceREST.request(request,""){response ->
            if (response.isHttpSuccess()){
                val defaultResponse : DefaultResponse?
                try {
                    defaultResponse = DefaultResponse.create(response.getDataString())
                    completion(defaultResponse, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }


    fun resetPassword(email: String, newPassword: String, confirmNewPassword : String ,resetPasswordCode : String, completion: (Boolean, Exception?) -> Unit){

        val params = mapOf("email" to email, "newPassword" to newPassword, "confirmNewPassword" to confirmNewPassword, "confirmation_password" to resetPasswordCode)

        val request = HttpRequest("forgotEmail/changePassword", params, HttpRequestMethod.POST, false)

        serviceREST.request(request, ""){response ->
            val success = response.isHttpSuccess()
            if (success){
                completion(success, null)
            }else{
                completion(false, response.getFError())
            }
        }

    }

}