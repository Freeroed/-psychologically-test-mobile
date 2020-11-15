package com.example.cursach.rest

import com.example.cursach.rest.request.LoginDto
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
}