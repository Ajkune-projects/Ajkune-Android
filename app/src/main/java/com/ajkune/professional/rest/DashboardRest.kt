package com.ajkune.professional.rest

import android.util.Log
import com.ajkune.professional.architecture.models.*
import com.ajkune.professional.di.anotation.ForServiceRest
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class DashboardRest @Inject constructor(@ForServiceRest private var serviceRest: ServiceRest, private var baseAccountManager: BaseAccountManager) {


    fun getAllActiveCategories(completion: (List<Category>?, Exception?) -> Unit){

        val request = HttpRequest("categories", null, HttpRequestMethod.GET,false)


        serviceRest.request(request, baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val category : List<Category>?
                try {
                    category = Category.createArray(response.getJsonArray())
                    completion(category, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun getActiveProducts(completion: (List<Product>?, Exception?) -> Unit){

        val request = HttpRequest("products", null, HttpRequestMethod.GET,false)

        serviceRest.request(request,baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val category : List<Product>?
                try {
                    category = Product.createArray(response.getJsonArray())
                    completion(category, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun getProductsByCategoryId(categoryId : Int, completion: (List<Product>?, Exception?) -> Unit){

        val request = HttpRequest("categories/$categoryId", null, HttpRequestMethod.GET,false)

        serviceRest.request(request, baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val category : List<Product>?
                try {
                    category = Product.createArray(response.getJsonArray())
                    completion(category, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun getUserById(completion: (UserById?, Exception?) -> Unit){
        val token = baseAccountManager.token!!
        val params = mapOf("token" to token)

        val request = HttpRequest("get_user", params, HttpRequestMethod.GET,false)

        serviceRest.request(request, baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val user : UserById?
                try {
                    user = UserById.create(response.getDataString())
                    completion(user, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun getAppointmentToken(completion: (AppointmentToken?, Exception?) -> Unit){

        val params = mapOf("grant_type" to "password", "username" to "elysgroupdev@gmail.com", "password" to "elysgroupdev1")

        val request = HttpRequest("https://api.shore.com/v2/tokens", params, HttpRequestMethod.POST,true)

        serviceRest.request(request,""){response ->
            if (response.isHttpSuccess()){
                val appointmentToken : AppointmentToken?
                try {
                    appointmentToken = AppointmentToken.create(response.getDataString())
                    completion(appointmentToken, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun getAllAppointments(appointmentToken : String,completion: (List<AllAppointment>?, Exception?) -> Unit){

        val request = HttpRequest("https://api.shore.com/v2/appointments", null, HttpRequestMethod.GET,true)

        serviceRest.request(request,appointmentToken){response ->
            if (response.isHttpSuccess()){
                val allAppointment : List<AllAppointment>?
                try {
                    allAppointment = AllAppointment.createArray(response.getAllAppointments())
                    completion(allAppointment, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

}