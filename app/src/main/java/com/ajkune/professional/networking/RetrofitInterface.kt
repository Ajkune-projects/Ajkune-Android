package com.ajkune.professional.networking

import com.ajkune.professional.architecture.models.AppointmentBody
import com.ajkune.professional.architecture.models.BodyV2
import com.ajkune.professional.architecture.models.VerifyUserAccountBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitInterface {

    @POST("appointments")
    fun addNewAppointment(@Header("Content-Type") type: String, @Header("Authorization") bearerToken : String, @Body data : BodyV2): Call<Boolean>

    @POST("profile/verification")
    fun verifyUserAccountRetrofit(@Header("Authorization") bearerToken : String, @Body data : VerifyUserAccountBody): Call<Boolean>
}