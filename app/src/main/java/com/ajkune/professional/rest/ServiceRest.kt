package com.ajkune.professional.rest

import android.util.Log
import com.ajkune.professional.utilities.data.Constants
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import com.ajkune.professional.utilities.helpers.NetworkUtil
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class ServiceRest @Inject constructor(private val networkUtil: NetworkUtil, private var baseAccountManager: BaseAccountManager) {

    fun request(request: HttpRequest, token : String, completion: (response: HttpResponse) -> Unit) {

        val client = OkHttpClient()

        val requestBuilder = request.getBuilder()

        if (baseAccountManager.isLogged()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        //requestBuilder.addHeader("Accept", "application/json")

        client.newCall(requestBuilder.build()).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {

                if (!networkUtil.isConnected()) {
                    val response = HttpResponse(call, null, IOException("No Internet Connection"))
                    completion(response)
                }else {
                    Log.e("REST_CODE",e.toString())
                    val response = HttpResponse(call, null, e)
                    completion(response)
                }
            }

            override fun onResponse(call: Call?, response: Response?) {
                val httpResponse = HttpResponse(call, response, null)
                Log.e("REST_CODE",response?.code().toString())

                completion(httpResponse)
            }
        })
    }
}

class HttpRequest(private var url: String, param: Map<String, Any>? = null, private var method: HttpRequestMethod = HttpRequestMethod.GET, var isUrlFromAppointment: Boolean) {

    private var parameters = mapOf<String, Any>()

    init {
        if (param != null) {
            this.parameters = param
        }
    }

    fun getBuilder() : Request.Builder {
        var url : String = ""

        url = if (!isUrlFromAppointment){
            Constants.baseUrl + this.url
        }else{
            this.url
        }

        val httpUrl = HttpUrl.parse(url)!!.newBuilder()

        val requestBuilder = Request.Builder()

        if (method == HttpRequestMethod.GET) {

            for ((key, value) in parameters) {
                httpUrl.addQueryParameter(key, value.toString())
            }

            requestBuilder.url(httpUrl.build())
        } else {
            val json = Gson().toJson(parameters)
            val requestBody = RequestBody.create(MediaType.parse("application/json"), json)

            requestBuilder.url(url)
                .method(method.toString(), requestBody)
        }

        return requestBuilder
    }
}

data class HttpResponse (val call: Call?, val response: Response?, val error: IOException?){

    private val iResponse by lazy {
        response?.body()?.string()
    }

    fun isHttpSuccess() : Boolean {
        return response?.code() == 200
    }

    fun getFError() : Exception? {

        if (this.error != null) {
            return error
        }

        if (response != null) {
            try {
                return  Exception(getMessageString())
            } catch (ex: Exception) {
                println(ex.localizedMessage)
            }
        }

        return Exception("")
    }

    fun getDataString() : String {
        return try {
            JSONObject(iResponse).toString()
        }
        catch (ex: Exception) {
            ""
        }
    }

    fun getJsonArray() : String {
        return try {
            JSONArray(iResponse).toString()
        }
        catch (ex: Exception) {
            ""
        }
    }

    fun getAllAppointments() : String {
        return try {
            val o = JSONObject(iResponse)
            o.optString("data") ?: ""
        }
        catch (ex: Exception) {
            ""
        }
    }

    fun getDataJSON() : JSONObject {
        return try {
            val o = JSONObject(iResponse)

            o.getJSONObject("data")
        } catch (ex: Exception) {
            JSONObject("{}")
        }
    }

    fun getPaginationString() : String {
        return try {
            val o = JSONObject(iResponse)
            o.optString("pagination") ?: ""
        }
        catch (ex: Exception) {
            ""
        }
    }

    private fun getMessageString() : String {
        return try {
            val o = JSONObject(iResponse)

            o.optString("message") ?: ""
        }
        catch (ex: Exception) {
            ""
        }
    }
}

enum class HttpRequestMethod {
    GET, POST, PUT, PATCH, DELETE, HEAD
}