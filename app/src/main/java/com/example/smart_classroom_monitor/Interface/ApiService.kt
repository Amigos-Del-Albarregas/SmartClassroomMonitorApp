package com.example.smart_classroom_monitor.Interface

import com.example.smart_classroom_monitor.Model.Respuesta
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/obtenerDatosGenerales")
    suspend fun getDatosGenerales(): Respuesta

    @GET("/obtenerDatosModulo/{id}")
    suspend fun getDatosModulo(@Path("id") id: Int): Respuesta

    @GET("/getModulos")
    suspend fun getModulos(): Respuesta
}
