package com.example.cursach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_question.*

class Question : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        // выбор ответа "да"
        selectedYes.setOnClickListener {
            selectedYes.background =  getResources().getDrawable(R.color.selectedYelow)
            selectedNo.background =  getResources().getDrawable(R.color.mainGreen)
        }

        // выбор ответа "нет"
        selectedNo.setOnClickListener {
            selectedYes.background =  getResources().getDrawable(R.color.mainGreen)
            selectedNo.background =  getResources().getDrawable(R.color.selectedYelow)
        }
    }
}