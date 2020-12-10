package com.example.cursach

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.example.cursach.rest.ApiClient
import com.example.cursach.rest.response.ResultTestDto
import kotlinx.android.synthetic.main.activity_stat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class StatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)

        var apiClient = ApiClient()
        val context = this

        var userId = getIntent().getExtras()?.getString("userId")
        var isAdmin = getIntent().getExtras()?.getBoolean("isAdmin")!!
        if (isAdmin) userId = ""
        // получение данных
        if (userId != null) {
            apiClient.getApiService(this).getResults(userId)
                .enqueue(object : Callback<ArrayList<ResultTestDto>> {
                    override fun onFailure(call: Call<ArrayList<ResultTestDto>>, t: Throwable) {
                        Toast.makeText( context,"FAIL", Toast.LENGTH_SHORT).show()
                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    @SuppressLint("ResourceAsColor")
                    override fun onResponse(call: Call<ArrayList<ResultTestDto>>, response: Response<ArrayList<ResultTestDto>>) {
                        var resultInfo = response.body()
                        var i = 1
                        resultInfo?.forEach{
                            var btn = Button(context)
                            btn.id = it.id.toInt()
                            if (i % 2 == 0) {
                                btn.setBackgroundColor(resources.getColor(R.color.mainGreen))
                            } else {
                                btn.setBackgroundColor(resources.getColor(R.color.selectedYelow))
                            }

                            val instant = Instant.parse(it.finishedAt)
                            val formatter: DateTimeFormatter =
                                DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm").withZone(ZoneId.systemDefault())
                            btn.setText("Результат за " + formatter.format(instant))

                            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                            val scrollView = findViewById<ScrollView>(R.id.results)
                            params.setMargins(50, 0, 50, 30)
                            params.gravity = Gravity.CENTER
                            btn.layoutParams = params

                            btn.gravity = Gravity.CENTER_HORIZONTAL

                            // получение конкретного результата
                            btn.setOnClickListener({ v ->
                                val intent = Intent(context, TestResult::class.java)
                                intent.putExtra("result", it)
                                startActivity(intent)
                            })

                            //mainLayout.addView(btn)
                            btns.addView(btn)
                            i++
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