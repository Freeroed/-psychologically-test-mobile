package com.example.cursach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cursach.rest.ApiClient
import com.example.cursach.rest.response.ResultTestDto
import kotlinx.android.synthetic.main.activity_stat.goBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)

        var apiClient = ApiClient()
        val context = this

        var userId = getIntent().getExtras()?.getString("userId")
        // получение данных
        if (userId != null) {
            apiClient.getApiService(this).getResults(userId.toInt())
                .enqueue(object : Callback<ArrayList<ResultTestDto>> {
                    override fun onFailure(call: Call<ArrayList<ResultTestDto>>, t: Throwable) {
                        Toast.makeText( context,"FAIL", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ArrayList<ResultTestDto>>, response: Response<ArrayList<ResultTestDto>>) {
                        val resultInfo = response.body()
                        resultInfo?.forEach{
                            Log.e("resultInfo", it.toString())
                        }
                    }
                })
        }

        // клик по кнопке "назад"
        goBack.setOnClickListener {
            onBackPressed()
        }
    }
}