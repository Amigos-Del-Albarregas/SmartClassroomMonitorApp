package com.example.smart_classroom_monitor.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.smart_classroom_monitor.R
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redirectToOtraActivity()
    }

    private fun redirectToOtraActivity() {
        val intent = Intent(this, Pagina_Principal::class.java)
        startActivity(intent)
        finish() // Opcional, si deseas cerrar la actividad actual después de la redirección
    }
}