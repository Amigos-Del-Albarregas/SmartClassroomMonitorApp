package com.example.smart_classroom_monitor.Firebase

import android.content.Context
import androidx.preference.PreferenceManager

class TokenManager(private val context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun clearToken() {
        val editor = sharedPreferences.edit()
        editor.remove("token")
        editor.apply()
    }
}
