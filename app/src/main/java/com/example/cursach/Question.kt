package com.example.cursach

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginRight
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
    private lateinit var timer : CountDownTimer
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        this.context = this

        apiClient = ApiClient()
        goBack.visibility = View.INVISIBLE

        var questions : ArrayList<QuestionDto> = intent.getSerializableExtra("questions") as ArrayList<QuestionDto>
        var currentQuestionNumber = 1;
        var currentQuestion: QuestionDto = questions[currentQuestionNumber - 1];
        question.text = currentQuestion.question;
        questionNumber.text = "Вопрос " + currentQuestionNumber + "/" + questions.size
        var selectedAnswer: Boolean = false;
        goNext.isEnabled = false
        var answers: ArrayList<AnswerDto> = ArrayList()


        this.timer = object: CountDownTimer(600000L, 1000) {
            override fun onFinish() {
                Toast.makeText( context,"ВРЕМЯ ВЫШЛО", Toast.LENGTH_SHORT).show()
                val stopTestDialog = AlertDialog.Builder(context)
                stopTestDialog.setTitle("Время на проходение опроса вышло. Начните с начала")
                stopTestDialog.setPositiveButton("Ок", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val intent = Intent(context, TestDescriptionActivity::class.java)
                        startActivity(intent)
                    }
                })
                stopTestDialog.setCancelable(false)
                stopTestDialog.show()


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
            goNext.visibility = View.VISIBLE
        }

        // выбор ответа "нет"
        selectedNo.setOnClickListener {
            selectedYes.background =  getResources().getDrawable(R.color.mainGreen)
            selectedNo.background =  getResources().getDrawable(R.color.selectedYelow)
            selectedAnswer = false
            goNext.isEnabled = true
            goNext.visibility = View.VISIBLE

        }

        // клик по кнопке "назад"
       // goBack.setOnClickListener {

       // }

        // клик по кнопке "вперед"
        goNext.setOnClickListener {
            goNext.isEnabled = false
            if (currentQuestionNumber < questions.size) {
                currentQuestionNumber++;
                if (currentQuestionNumber === questions.size) {
                    goNext.setImageResource(R.drawable.to_result_170_50)
                    goNext.rotation = 0f


                }

                currentQuestion = questions[currentQuestionNumber -1]
                question.text = currentQuestion.question

                questionNumber.text = "Вопрос " + currentQuestionNumber + "/" + questions.size

                goNext.visibility = View.INVISIBLE
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
                            intent.putExtra("previousPage", "test_desc")
                            startActivity(intent)
                            println(result)
                            Toast.makeText( context,"Ваш ответ сохранён", Toast.LENGTH_SHORT).show()
                        }
                    })
            }

        }
    }

    override fun onBackPressed() {
        val stopTestDialog = AlertDialog.Builder(this)
        stopTestDialog.setTitle("Вы уверены, что хотите завершить тест?")
        stopTestDialog.setPositiveButton("Да", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        });
        stopTestDialog.setNegativeButton("Нет", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, whitch: Int) {
                dialog?.cancel()
            }
        })
        stopTestDialog.show()
    }
}