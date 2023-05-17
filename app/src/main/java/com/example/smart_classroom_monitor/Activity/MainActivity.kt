package com.example.smart_classroom_monitor.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.smart_classroom_monitor.Caller.HeaderBack
import com.example.smart_classroom_monitor.Firebase.TokenManager
import com.example.smart_classroom_monitor.Model.Rol
import com.example.smart_classroom_monitor.Model.User
import com.example.smart_classroom_monitor.R
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    val apiService = HeaderBack().conexion()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tokenManager = TokenManager(this)
        val token = tokenManager.getToken()
        if (token != null) {
            redirectToOtraActivity()
        }
        // access the items of the list
        val listModules = runBlocking {
            apiService.obtenerRol()
        }
        //boton
        val button = findViewById<Button>(R.id.button)
        if(button != null){
            button
        }


        // access the spinner
        val spinner = findViewById<Spinner>(R.id.mySpinnerRol)
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
                    Toast.makeText(this@MainActivity,
                        getString(R.string.selected_item) + " " +
                                "" + listModules[position].nombre, Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    fun crearUser(view: View){

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
                    val textName: EditText = findViewById(R.id.editTextText) as EditText
                    val spinner = findViewById<Spinner>(R.id.mySpinnerRol)
                    val rol: Int = getSelectedSpinnerValue(spinner)
                    var usuario: User = User(textName.text.toString(),tokenFirebase,rol)
                    val result = runBlocking {
                        apiService.crearUsuarios(usuario)
                    }
                    if (result){
                        tokenManager.saveToken(tokenFirebase)
                        redirectToOtraActivity()
                    }
                } else {
                    Log.d("TOKEN existe",token)
                }
            }
        }
    }

    private fun getSelectedSpinnerValue(spinner: Spinner): Int {
        return spinner.selectedItemPosition + 1
    }

    private fun redirectToOtraActivity() {
        val intent = Intent(this, Pagina_Principal::class.java)
        startActivity(intent)
        finish() // Opcional, si deseas cerrar la actividad actual después de la redirección
    }
}