package com.example.smart_classroom_monitor.Interface

import com.example.smart_classroom_monitor.Model.Respuesta
import com.example.smart_classroom_monitor.Model.Modulos
import com.example.smart_classroom_monitor.Model.Rol
import com.example.smart_classroom_monitor.Model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/obtenerDatosGenerales")
    suspend fun getDatosGenerales(): Respuesta?

    @GET("/cambiarAula/{aula}")
    suspend fun getDatosModulo(@Path("aula") aula: String): String?

    @GET("/obtenerModulos")
    suspend fun obtenerModulos(): Array<Modulos>?

    @GET("/obtenerRol")
    suspend fun obtenerRol(): Array<Rol>

    @POST("/crearUsuario")
    suspend fun crearUsuarios(@Body user: User): Boolean
}
