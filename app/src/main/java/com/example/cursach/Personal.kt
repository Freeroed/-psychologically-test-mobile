package com.example.cursach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cursach.rest.ApiClient
import com.example.cursach.rest.response.AccountDto
import kotlinx.android.synthetic.main.activity_personal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Personal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var apiClient: ApiClient

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

        apiClient = ApiClient()
        val context = this
        apiClient.getApiService(this).getAccount()
            .enqueue(object : Callback<AccountDto> {
                override fun onFailure(call: Call<AccountDto>, t: Throwable) {
                    Toast.makeText( context,"FAIL", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<AccountDto>, response: Response<AccountDto>) {
                    val userInfo = response.body()
                    userEmail.setText(userInfo?.email)
                    userName.setText(userInfo?.lastName + " " + userInfo?.firstName)
                }
            })
    }
}