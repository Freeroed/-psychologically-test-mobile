package com.example.cursach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class TestDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_description)

        // клик по кнопке "Начать тест" - переход на страницу первого вопроса
        goTest.setOnClickListener {
            val intent = Intent(this, Question::class.java)
            startActivity(intent)
        }
    }
}