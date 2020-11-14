package com.example.cursach

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cursach.rest.ApiClient
import com.example.cursach.utils.SessionManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // клик по кнопке "Начать тест" - переход на страницу описания теста
        goTest.setOnClickListener {
            val intent = Intent(this, TestDescriptionActivity::class.java)
            startActivity(intent)
        }

        // клик по кнопке "Личный кабинет"
        goPersonal.setOnClickListener {
            // проверка авторизации
            sessionManager = SessionManager(this)
            var token :String? = sessionManager.getAuthToken()
            if (token != null) {
                val intent = Intent(this, Personal::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // обработка нажатия кнопки "назад"
    override fun onBackPressed() {
        val quitDialog = AlertDialog.Builder(this)
        quitDialog.setTitle("Вы уверены, что хотите выйти?")
        quitDialog.setPositiveButton("Да", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                finish() // закрыть приложение
            }
        })
        quitDialog.setNegativeButton("Нет", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.cancel()  //закрыть диалоговое окно
            }
        })
        quitDialog.show()
    }

}