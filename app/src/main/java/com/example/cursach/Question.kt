package com.example.cursach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.cursach.rest.ApiClient
import com.example.cursach.rest.request.AnswerDto
import com.example.cursach.rest.response.QuestionDto
import com.example.cursach.rest.response.ResultTestDto
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_question.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Question : AppCompatActivity() {
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val context = this

        apiClient = ApiClient()

        var questions : ArrayList<QuestionDto> = intent.getSerializableExtra("questions") as ArrayList<QuestionDto>
        var currentQuestionNumber = 1;
        var currentQuestion: QuestionDto = questions[currentQuestionNumber - 1];
        question.text = currentQuestion.question;
        questionNumber.text = "Вопрос " + currentQuestionNumber + "/" + questions.size
        var selectedAnswer: Boolean = false;
        goNext.isEnabled = false
        var answers: ArrayList<AnswerDto> = ArrayList()


        val timer = object: CountDownTimer(240000L, 1000) {
            override fun onFinish() {
                // TODO Остановить текст
                Toast.makeText( context,"ВРЕМЯ ВЫШЛО", Toast.LENGTH_SHORT).show()
            }

            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished - minutes*60000) / 1000
                stopwatch.text = minutes.toString() + ":" + seconds.toString()
            }

        }
        timer.start()
        // выбор ответа "да"
        selectedYes.setOnClickListener {
            selectedYes.background =  getResources().getDrawable(R.color.selectedYelow)
            selectedNo.background =  getResources().getDrawable(R.color.mainGreen)
            selectedAnswer = true;
            goNext.isEnabled = true
        }

        // выбор ответа "нет"
        selectedNo.setOnClickListener {
            selectedYes.background =  getResources().getDrawable(R.color.mainGreen)
            selectedNo.background =  getResources().getDrawable(R.color.selectedYelow)
            selectedAnswer = false;
            goNext.isEnabled = true
        }

        // клик по кнопке "назад"
        goBack.setOnClickListener {

        }

        // клик по кнопке "вперед"
        goNext.setOnClickListener {
            if (currentQuestionNumber < questions.size) {
                currentQuestionNumber++;
                currentQuestion = questions[currentQuestionNumber -1]
                question.text = currentQuestion.question

                questionNumber.text = "Вопрос " + currentQuestionNumber + "/" + questions.size
                goNext.isEnabled = false
                selectedYes.background =  getResources().getDrawable(R.color.mainGreen)
                selectedNo.background =  getResources().getDrawable(R.color.mainGreen)
                val answer = AnswerDto(selectedAnswer, currentQuestion.id)
                answers.add(answer)
            } else {
                val answer = AnswerDto(selectedAnswer, currentQuestion.id)
                answers.add(answer)
                apiClient.getApiService(this).solveTest(answers)
                    .enqueue( object  : Callback<ResultTestDto> {
                        override fun onFailure(call: Call<ResultTestDto>, t: Throwable) {
                            println(t.stackTrace)
                            println("****************************************")
                            println(t.message)
                            println("****************************************")
                            println(t.localizedMessage)
                            println("****************************************")
                            Toast.makeText( context,"FAIL", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<ResultTestDto>,
                            response: Response<ResultTestDto>
                        ) {
                            val result = response.body();
                            val intent = Intent(context, TestResult::class.java)
                            intent.putExtra("result", result)
                            startActivity(intent)
                            println(result)
                            Toast.makeText( context,"Ваш ответ сохранён", Toast.LENGTH_SHORT).show()
                        }
                    })
            }

        }
    }
}