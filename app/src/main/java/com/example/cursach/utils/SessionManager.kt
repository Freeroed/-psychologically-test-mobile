package com.example.cursach.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.cursach.R

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val AUTH_TOKEN = "auth_token"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return prefs.getString(AUTH_TOKEN, null)
    }

    fun cleanAuthToken() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}