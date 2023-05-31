package com.example.smart_classroom_monitor.Caller

import com.example.smart_classroom_monitor.Constant.Constant
import com.example.smart_classroom_monitor.Interface.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HeaderBack {

    fun conexion(): ApiService {
        val httpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS) // Set read timeout to 60 seconds
            .connectTimeout(60, TimeUnit.SECONDS) // Set connection timeout to 60 seconds // Add logging interceptor if needed
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        return retrofit.create(ApiService::class.java)

    }
}