package com.example.cursach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cursach.rest.ApiClient
import com.example.cursach.rest.request.LoginDto
import com.example.cursach.rest.response.TokenDto
import com.example.cursach.utils.SessionManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // клик по кнопке "Начать тест" - переход на страницу описания теста
        goTest.setOnClickListener {
            val intent = Intent(this, TestDescriptionActivity::class.java)
            startActivity(intent)
        }

        // клик по кнопке "Личный кабинет"
        goPersonal.setOnClickListener {
            // проверка авторизации
            sessionManager = SessionManager(this)
            var token :String? = sessionManager.getAuthToken()
            if (token != null) {
                val intent = Intent(this, Personal::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        }
    }
}