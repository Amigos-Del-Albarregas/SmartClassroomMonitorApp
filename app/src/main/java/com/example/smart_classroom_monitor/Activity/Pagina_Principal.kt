package com.example.smart_classroom_monitor.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.smart_classroom_monitor.R
import com.example.smart_classroom_monitor.Interface.ApiService
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class Pagina_Principal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina_principal)
        // access the items of the list
        val languages = resources.getStringArray(R.array.Languages)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.mySpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(this@Pagina_Principal,
                        getString(R.string.selected_item) + " " +
                                "" + languages[position], Toast.LENGTH_SHORT).show()
                    val text: TextView = findViewById(R.id.nombreNodo) as TextView
                    text.setText(languages[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        cambiarTextos()

    }

    fun cambiarTextos(){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.137.1:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val result = runBlocking {
            apiService.getMierda()
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
        }, 3000)
    }
}