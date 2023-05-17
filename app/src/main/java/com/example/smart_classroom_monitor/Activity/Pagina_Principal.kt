package com.example.smart_classroom_monitor.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smart_classroom_monitor.Caller.HeaderBack
import com.example.smart_classroom_monitor.Firebase.TokenManager
import com.example.smart_classroom_monitor.R
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.Timer
import java.util.TimerTask


class Pagina_Principal : AppCompatActivity() {

    val apiService = HeaderBack().conexion()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().token.addOnCompleteListener {

            if (!it.isSuccessful){
                Log.e("TokenDetails","Token Failed to receive!")
            }

            var tokenFirebase = it.getResult()

            if (tokenFirebase != null) {
                val tokenManager = TokenManager(this)
                val token = tokenManager.getToken()
                if (token != null && token != tokenFirebase) {
                    // El token existe, úsalo
                    Log.d("TOKEN update",tokenFirebase)
                    tokenManager.saveToken(tokenFirebase)
                } else if(token == null){
                    // El token no existe
                    Log.d("TOKEN no existe",tokenFirebase)
                    tokenManager.saveToken(tokenFirebase)
                } else {
                    Log.d("TOKEN existe",token)
                }
            }
        }

        setContentView(R.layout.activity_pagina_principal)
        // access the items of the list
        val listModules = runBlocking {
            apiService.obtenerModulos()
        }

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.mySpinner)
        if (spinner != null) {
            var arrayModulos = arrayOf<String>()
            listModules.forEach { element ->
                arrayModulos += element.nombre
            }
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arrayModulos)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(this@Pagina_Principal,
                        getString(R.string.selected_item) + " " +
                                "" + listModules[position].nombre, Toast.LENGTH_SHORT).show()
                    val text: TextView = findViewById(R.id.nombreNodo) as TextView
                    text.setText(listModules[position].nombre)
                    cambiarTextos()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        cambiarTextos()
        deleteCache(this)
    }

    fun cambiarTextos(){

        val result = runBlocking {
            apiService.getDatosGenerales()
        }

        val textTemperature: TextView = findViewById(R.id.temperature) as TextView
        val textRuido: TextView = findViewById(R.id.ruido) as TextView
        val textPresion: TextView = findViewById(R.id.bares) as TextView

        textTemperature.setText(result.temperatura+"º")
        textRuido.setText(result.ruido+"dB")
        textPresion.setText(result.bares+"mb")
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                cambiarTextos()
            }
        }, 10000)
    }

    fun deleteCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteDir(dir)
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    deleteCache(context)
                }
            }, 31000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }
}