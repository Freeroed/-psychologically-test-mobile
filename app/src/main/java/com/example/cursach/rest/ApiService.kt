package com.example.cursach.rest

import com.example.cursach.rest.request.LoginDto
import com.example.cursach.rest.response.TokenDto
import com.example.cursach.utils.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST(Constants.AUTH_URL)
    @FormUrlEncoded
    fun authenticate(@Body request: LoginDto): Call<TokenDto>
}