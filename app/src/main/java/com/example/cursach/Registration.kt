package com.example.cursach

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.cursach.rest.ApiClient
import com.example.cursach.rest.response.AccountDto
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_test_description.goBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.util.*

class Registration : AppCompatActivity() {

    private lateinit var apiClient: ApiClient

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val calendar = Calendar.getInstance()

        // клик по кнопке "назад"
        goBack.setOnClickListener {
            onBackPressed()
        }

        // выбор даты рождения через календарь
        birthdayInput.setOnClickListener {
            val dataPicker = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                birthdayInput.setText("" + dayOfMonth + "." + (monthOfYear+1) + "." + year)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dataPicker.show()
        }

        // регистрация
        register.setOnClickListener {
            val context = this

            val email = emailInput.text.toString()
            val passwordFirst = passwordInput.text.toString()
            val passwordSecond = passwordRepeatInput.text.toString()
            val birthday = birthdayInput.text.toString()
            val gender = resources.getResourceEntryName(genderSelect.checkedRadioButtonId)
            val createdDate = Instant.now().toString()

            if (
                email.length == 0 ||
                passwordFirst.length == 0 ||
                passwordSecond.length == 0 ||
                birthday.length == 0 ||
                gender.length == 0
            ) {
                Toast.makeText( context, "Все поля обязательны для заполнения", Toast.LENGTH_SHORT).show()
            } else {
                if (passwordFirst != passwordSecond) {
                    Toast.makeText( context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                } else {
                    apiClient = ApiClient()

                    var body = AccountDto(
                        login = email,
                        email = email,
                        password = passwordFirst,
                        activated = true,
                        firstName = "тест",
                        lastName = "тест",
                        gender = gender,
                        birthDate = createdDate,
                        createdDate = createdDate
                    )

                    apiClient.getApiService(this).registration(body)
                        .enqueue(object : Callback<Void> {
                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText( context,"FAIL", Toast.LENGTH_SHORT).show()
                                Log.e("1", "1")
                            }

                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.code() == 201) {
                                    Toast.makeText( context,"Вы успешно зарегестрированы", Toast.LENGTH_SHORT).show()
                                }
                                if (response.code() == 400) {
                                    Toast.makeText( context,response.message().toString(), Toast.LENGTH_SHORT).show()
                                }
                            }

                        })
                }
            }
        }

    }
}