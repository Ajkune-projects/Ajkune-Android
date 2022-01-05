package com.ajkune.professional.networking

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object AddAppointmentRetrofit {
    private var retrofit: Retrofit? = null
    fun service(): RetrofitInterface {
        if (retrofit == null) {
            val gson = GsonBuilder()
                .create()
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.MINUTES)
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl("https://api.shore.com/v2/")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!.create(RetrofitInterface::class.java)
    }
}