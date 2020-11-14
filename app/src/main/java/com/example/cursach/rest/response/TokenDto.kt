package com.example.cursach.rest.response

import com.google.gson.annotations.SerializedName

data class TokenDto (
    @SerializedName("id_token")
    var token: String,

    @SerializedName("status_code")
    var statusCode: Int
)