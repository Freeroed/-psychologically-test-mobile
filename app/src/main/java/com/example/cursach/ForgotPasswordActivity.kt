package com.example.cursach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cursach.rest.ApiClient
import com.example.cursach.rest.request.LoginDto
import com.example.cursach.rest.request.ResetPassDto
import com.example.cursach.rest.response.TokenDto
import kotlinx.android.synthetic.main.activity_edit_user.*
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password.emailInput
import kotlinx.android.synthetic.main.activity_forgot_password.goBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // отправить
        sendEmail.setOnClickListener {

            apiClient = ApiClient()
            val context = this
            var email : String = emailInput.getText().toString()

            if (email?.length == 0) {
                Toast.makeText( context,"Введите email", Toast.LENGTH_SHORT).show()
            } else {
                apiClient.getApiService(this).resetPassword(ResetPassDto(mail = emailInput?.getText().toString()))
                    .enqueue(object : Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText( context,"FAIL", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.code() == 200) {
                                Toast.makeText( context,"Новый пароль отправлен Вам на почту", Toast.LENGTH_SHORT).show()
                                onBackPressed()
                            } else {
                                Toast.makeText( context,"Пользователь с таким email не найден", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }
        }

        // клик по кнопке "назад"
        goBack.setOnClickListener {
            onBackPressed()
        }
    }
}