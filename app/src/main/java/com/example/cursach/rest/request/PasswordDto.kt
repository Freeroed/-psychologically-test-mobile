package com.example.cursach.rest.request

import com.google.gson.annotations.SerializedName

data class PasswordDto (
    @SerializedName("currentPassword")
    var currentPassword: String,

    @SerializedName("newPassword")
    var newPassword: String


)