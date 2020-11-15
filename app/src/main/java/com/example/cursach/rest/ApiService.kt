package com.example.cursach.rest

import com.example.cursach.rest.request.AnswerDto
import com.example.cursach.rest.request.LoginDto
import com.example.cursach.rest.response.QuestionDto
import com.example.cursach.rest.response.ResultTestDto
import com.example.cursach.rest.response.AccountDto
import com.example.cursach.rest.response.TokenDto
import com.example.cursach.utils.Constants
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Constants.AUTH_URL)
    fun authenticate(@Body request: LoginDto): Call<TokenDto>

    @GET(Constants.ACCOUNT_URL)
    fun getAccount(): Call<AccountDto>

    @POST(Constants.REGISTER_URL)
    fun registration(@Body request: AccountDto): Call<Void>

    @POST(Constants.SOLVE_TEST_URL)
    fun solveTest(@Body answers: ArrayList<AnswerDto>): Call<ResultTestDto>

    @GET(Constants.QUESTIONS_URL)
    fun getQuestions(): Call<ArrayList<QuestionDto>>

}