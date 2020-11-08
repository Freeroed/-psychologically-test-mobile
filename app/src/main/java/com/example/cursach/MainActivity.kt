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
        val context = this
        //Инициализация Апи сервиса
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).authenticate(LoginDto(username = "admin", password = "admin"))
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

        // клик по кнопке "Начать тест" - переход на страницу описания теста
        goTest.setOnClickListener {
            val intent = Intent(this, TestDescriptionActivity::class.java)
            startActivity(intent)
        }
    }
}