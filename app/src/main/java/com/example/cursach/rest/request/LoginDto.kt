package com.example.cursach.rest.request

import com.google.gson.annotations.SerializedName

data class LoginDto (
    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String


)