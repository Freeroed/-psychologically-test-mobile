package com.example.cursach.rest.response

import com.google.gson.annotations.SerializedName

data class AccountDto (
    @SerializedName("email")
    var email: String,

    @SerializedName("firstName")
    var firstName: String,

    @SerializedName("lastName")
    var lastName: String
)