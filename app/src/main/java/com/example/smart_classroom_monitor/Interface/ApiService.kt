package com.example.smart_classroom_monitor.Interface

import com.example.smart_classroom_monitor.Model.Respuesta
import retrofit2.http.GET

interface ApiService {
    @GET("/obtenerMierda")
    suspend fun getMierda(): Respuesta
}
