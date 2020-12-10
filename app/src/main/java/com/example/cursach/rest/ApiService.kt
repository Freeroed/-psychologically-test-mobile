package com.example.cursach.rest

import com.example.cursach.rest.request.AnswerDto
import com.example.cursach.rest.request.LoginDto
import com.example.cursach.rest.request.PasswordDto
import com.example.cursach.rest.request.ResetPassDto
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

    @GET(Constants.RESULTS_URL)
    fun getResults(@Query("userId.equals") id: String?, @Query("page") page: Int?, @Query("size") size: Int?): Call<ArrayList<ResultTestDto>>

    @POST(Constants.USER_UPDATE_URL)
    fun updateUser(@Body request: AccountDto): Call<Void>

    @POST(Constants.CHANGE_PASSWORD__URL)
    fun changePassword(@Body request: PasswordDto): Call<Void>

    @POST(Constants.RESET_PASSWORD_URL)
    fun resetPassword(@Body request: ResetPassDto): Call<Void>

}