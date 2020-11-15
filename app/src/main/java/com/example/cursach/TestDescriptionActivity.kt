package com.example.cursach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cursach.rest.ApiClient
import com.example.cursach.rest.response.QuestionDto
import com.example.cursach.rest.response.TokenDto
import com.example.cursach.utils.SessionManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.goTest
import kotlinx.android.synthetic.main.activity_test_description.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TestDescriptionActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_description)

        val context = this

        apiClient = ApiClient()
        sessionManager = SessionManager(this)



        // клик по кнопке "Начать тест" - переход на страницу первого вопроса
        goTest.setOnClickListener {
            apiClient.getApiService(this).getQuestions()
                .enqueue(object : Callback<ArrayList<QuestionDto>> {
                override fun onFailure(call: Call<ArrayList<QuestionDto>>, t: Throwable) {
                    Toast.makeText( context,"FAIL", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ArrayList<QuestionDto>>, response: Response<ArrayList<QuestionDto>>) {
                    val questions = response.body();

                    if (questions != null && questions.isNotEmpty()) {
                        Toast.makeText( context,"Закгружено вопросов : " + questions.size, Toast.LENGTH_LONG).show()
                        val intent = Intent(context, Question::class.java)
                        intent.putExtra("questions", questions)
                        startActivity(intent)
                    } else {
                        Toast.makeText( context,"Вопросы не найдены", Toast.LENGTH_LONG).show()
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