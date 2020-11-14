package com.example.cursach

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_test_description.*
import kotlinx.android.synthetic.main.activity_test_description.goBack
import java.util.*

class Registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val calendar = Calendar.getInstance()

        // клик по кнопке "назад"
        goBack.setOnClickListener {
            onBackPressed()
        }

        birthdayInput.setOnClickListener {
            val dataPicker = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                birthdayInput.setText("" + dayOfMonth + "." + (monthOfYear+1) + "." + year)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dataPicker.show()
        }

    }
}