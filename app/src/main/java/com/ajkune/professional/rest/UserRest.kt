package com.ajkune.professional.rest

import android.util.Log
import com.ajkune.professional.architecture.models.User
import com.ajkune.professional.di.anotation.ForServiceRest
import com.ajkune.professional.di.anotation.ForUserRest
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import java.lang.Exception
import javax.inject.Inject

@ForUserRest
class UserRest @Inject constructor(@ForServiceRest private var serviceREST: ServiceRest, private var baseAccountManager: BaseAccountManager) {


    fun login(userName: String, password: String, completion: (User?, Exception?) -> Unit){

        val params = mapOf("email" to userName, "password" to password)

        val request = HttpRequest("login", params, HttpRequestMethod.POST)

        serviceREST.request(request){response ->
            if (response.isHttpSuccess()){
                val account : User?
                try {
                    account = User.create(response.getDataString())
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

}