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

    fun getProductById(productId : Int,completion: (List<Product>?, Exception?) -> Unit){

        val request = HttpRequest("products/$productId", null, HttpRequestMethod.GET,false)

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

    fun getOfferById(offerId : Int,completion: (List<Product>?, Exception?) -> Unit){

        val request = HttpRequest("offers/$offerId", null, HttpRequestMethod.GET,false)

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

    fun filterProductsOrOffers(minPrice : Int, maxPrice : Int, type : String, completion: (List<Product>?, Exception?) -> Unit){

        val request = HttpRequest("filter/$minPrice/$maxPrice/$type", null, HttpRequestMethod.GET,false)

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

    fun filterOffers(minPrice : Int, maxPrice : Int, type : String, completion: (List<Offer>?, Exception?) -> Unit){

        val request = HttpRequest("filter/$minPrice/$maxPrice/$type", null, HttpRequestMethod.GET,false)

        serviceRest.request(request, baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val offer : List<Offer>?
                try {
                    offer = Offer.createArray(response.getJsonArray())
                    completion(offer, null)
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

    fun getSettings(completion: (Settings?, Exception?) -> Unit){

        val request = HttpRequest("settings", null, HttpRequestMethod.GET,false)

        serviceRest.request(request,baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val settings : Settings?
                try {
                    settings = Settings.create(response.getDataString())
                    completion(settings, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun getAppointmentToken(username : String, password : String,completion: (AppointmentToken?, Exception?) -> Unit){

        val params = mapOf("grant_type" to "password", "username" to username, "password" to password)

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

        val request = HttpRequest("https://api.shore.com/v2/appointments?page%500Bnumber%500D=200&page%5Bsize%5D=1000", null, HttpRequestMethod.GET,true)

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

    fun getAppointmentByDate(appointmentToken : String, date : String, nextDate : String,completion: (List<AllAppointment>?, Exception?) -> Unit){


        val request = HttpRequest("https://api.shore.com/v2/appointments?filter[starts_at.gt]=$date&filter[starts_at.lt]=$nextDate&page%5Bsize%5D=60&filter[deleted]=false", null, HttpRequestMethod.GET,true)

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

    fun addNewAppointment(appointmentToken : String, appointmentBody : AppointmentBody,completion: (Boolean, Exception?) -> Unit){

        val params = mapOf("data" to appointmentBody)
        val request = HttpRequest("https://api.shore.com/v2/appointments", params, HttpRequestMethod.POST,true)
        request.getBuilder().addHeader("Content-Type", "application/vnd.api+json")
        serviceRest.request(request,appointmentToken){response ->
            val success = response.isHttpSuccess()
            if (success){
                completion(success, null)
            }else{
                completion(false, response.getFError())
            }
        }
    }

    fun addNewAppointmentInDashboard(startTime : String, title: String,completion: (Boolean, Exception?) -> Unit){

        val params = mapOf("starts_at" to startTime, "title" to title)
        val request = HttpRequest("appointment/new", params, HttpRequestMethod.POST,false)

        serviceRest.request(request,baseAccountManager.token!!){response ->
            val success = response.isHttpSuccess()
            if (success){
                completion(success, null)
            }else{
                completion(false, response.getFError())
            }
        }
    }

    fun addCommentForProduct(productId : Int, title : String, comment : String,completion: (List<Product>?, Exception?) -> Unit){

        val params = mapOf("product_id" to productId, "title" to title, "comment" to comment)
        val request = HttpRequest("comment/new", params, HttpRequestMethod.POST,false)

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

    fun addCommentForOffer(productId : Int, title : String, comment : String,completion: (List<Product>?, Exception?) -> Unit){

        val params = mapOf("product_id" to productId, "title" to title, "comment" to comment)
        val request = HttpRequest("commentOffer/new", params, HttpRequestMethod.POST,false)

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

    fun getAllOffers(completion: (List<Offer>?, Exception?) -> Unit){

        val request = HttpRequest("offers", null, HttpRequestMethod.GET,false)

        serviceRest.request(request,baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val offer : List<Offer>?
                try {
                    offer = Offer.createArray(response.getJsonArray())
                    completion(offer, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun getBanner(completion: (List<Offer>?, Exception?) -> Unit){

        val request = HttpRequest("banner", null, HttpRequestMethod.GET,false)

        serviceRest.request(request,baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val offer : List<Offer>?
                try {
                    offer = Offer.createArray(response.getJsonArray())
                    completion(offer, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun verifyUserProfileV2(verifyUserAccountBody : VerifyUserAccountBody,completion: (Boolean, Exception?) -> Unit){

        val params = mapOf("name" to verifyUserAccountBody.name, "last_name" to verifyUserAccountBody.lastName,
            "gender" to verifyUserAccountBody.gender, "date_of_birth" to verifyUserAccountBody.dateOfBirth,
            "phone" to verifyUserAccountBody.phone, "address" to verifyUserAccountBody.address,
            "street" to verifyUserAccountBody.street, "zip_code" to verifyUserAccountBody.zipCode,
            "country" to verifyUserAccountBody.country,
            "base64_img" to verifyUserAccountBody.base64Img)

        val request = HttpRequest("profile/verification", params, HttpRequestMethod.POST,false)

        serviceRest.request(request,baseAccountManager.token!!){response ->
            val success = response.isHttpSuccess()
            if (success){
                completion(success, null)
            }else{
                completion(false, response.getFError())
            }
        }
    }

    fun hasGifts(completion: (HasUserGiftsResponse?, Exception?) -> Unit){

        val request = HttpRequest("gift/hasGiftUser", null, HttpRequestMethod.GET,false)

        serviceRest.request(request,baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val userGiftsResponse : HasUserGiftsResponse?
                try {
                    userGiftsResponse = HasUserGiftsResponse.create(response.getDataString())
                    completion(userGiftsResponse, null)
                    Log.i("user", userGiftsResponse.toString())
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun getListOfGifts(completion: (List<Gift>?, Exception?) -> Unit){

        val request = HttpRequest("gift/list", null, HttpRequestMethod.GET,false)

        serviceRest.request(request,baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val gifs : List<Gift>?
                try {
                    gifs = Gift.createArray(response.getJsonArray())
                    completion(gifs, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun addGiftFromSpinner(giftId : Int,completion: (GiftAddedResponse?, Exception?) -> Unit){

        val params = mapOf("gift_list_id" to giftId)
        val request = HttpRequest("gift/add", params, HttpRequestMethod.POST,false)

        serviceRest.request(request,baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val gift : GiftAddedResponse?
                try {
                    gift = GiftAddedResponse.create(response.getDataString())
                    completion(gift, null)
                    Log.i("user", gift.toString())
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }

    fun getUserGifts(completion: (List<Gift>?, Exception?) -> Unit){

        val request = HttpRequest("gift/win", null, HttpRequestMethod.GET,false)

        serviceRest.request(request,baseAccountManager.token!!){response ->
            if (response.isHttpSuccess()){
                val gifs : List<Gift>?
                try {
                    gifs = Gift.createArray(response.getJsonArray())
                    completion(gifs, null)
                }catch (ex : Exception){
                    completion(null, ex)
                }
            }else{
                completion(null, response.getFError())
            }
        }
    }
}