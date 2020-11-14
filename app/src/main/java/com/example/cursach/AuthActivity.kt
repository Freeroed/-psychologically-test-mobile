package com.example.cursach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cursach.rest.ApiClient
import com.example.cursach.rest.request.LoginDto
import com.example.cursach.rest.response.TokenDto
import com.example.cursach.utils.SessionManager
import kotlinx.android.synthetic.main.activity_auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // авторизация
        login.setOnClickListener {
            val context = this
            // инициализация апи сервиса
            apiClient = ApiClient()
            sessionManager = SessionManager(this)
            var email : String = emailInput.getText().toString()
            var pass : String = passwordInput.getText().toString()
            apiClient.getApiService(this).authenticate(LoginDto(username = email, password = pass))
                .enqueue(object : Callback<TokenDto> {
                    override fun onFailure(call: Call<TokenDto>, t: Throwable) {
                        Toast.makeText( context,"FAIL", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<TokenDto>, response: Response<TokenDto>) {
                        val loginResponse = response.body()

                        if (loginResponse?.token != null) {
                            sessionManager.saveAuthToken(loginResponse.token)
                            Toast.makeText( context,"Авторизаця успешна", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText( context,"Ошибка авторизации", Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }
    }
}