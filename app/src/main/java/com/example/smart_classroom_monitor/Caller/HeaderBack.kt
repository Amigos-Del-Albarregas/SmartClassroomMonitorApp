package com.example.smart_classroom_monitor.Caller

import com.example.smart_classroom_monitor.Constant.Constant
import com.example.smart_classroom_monitor.Interface.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HeaderBack {

    fun conexion(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)

    }
}