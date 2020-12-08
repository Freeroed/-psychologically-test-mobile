package com.example.cursach

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cursach.rest.ApiClient
import com.example.cursach.rest.response.AccountDto
import com.example.cursach.utils.SessionManager
import kotlinx.android.synthetic.main.activity_edit_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditUserActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var apiClient: ApiClient
        sessionManager = SessionManager(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        // исходные данные
        apiClient = ApiClient()
        val context = this
        apiClient.getApiService(this).getAccount()
            .enqueue(object : Callback<AccountDto> {
                override fun onFailure(call: Call<AccountDto>, t: Throwable) {
                    Toast.makeText( context,"FAIL", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<AccountDto>, response: Response<AccountDto>) {
                    val userInfo = response.body()
                    emailInput.setText(userInfo?.email)
                    nameInput.setText(userInfo?.firstName)
                    birthdayInput.setText(userInfo?.birthDate)
                    Log.e("body", userInfo.toString())
                    if (userInfo?.gender == "MALE") {
                        genderSelect.check(MALE.id)
                    } else {
                        genderSelect.check(FEMALE.id)
                    }

                    userId = userInfo?.id!!
                }
            })

        // выбор даты рождения через календарь
        val calendar = Calendar.getInstance()
        birthdayInput.setOnClickListener {
            val dataPicker = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    birthdayInput.setText("" + dayOfMonth + "." + (monthOfYear+1) + "." + year)
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dataPicker.show()
        }

        // клик по кнопке "назад"
        goBack.setOnClickListener {
            onBackPressed()
        }
    }
}