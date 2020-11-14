package com.example.cursach.rest.response

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class ResultTestDto(

    @SerializedName("id")
    val id: Long,

    @SerializedName("userId")
    val userId: Long,
    @SerializedName("stuckType")
    val stuckType: Long,

    @SerializedName("pedanticType")
    val pedanticType: Long,

    @SerializedName("hyperthymicType")
    val hyperthymicType: Long,

    @SerializedName("excitableType")
    val excitableType: Long,

    @SerializedName("emotiveType")
    val emotiveType: Long,

    @SerializedName("emotionallyExaltedType")
    val emotionallyExaltedType: Long,

    @SerializedName("dysthymicType")
    val dysthymicType: Long,

    @SerializedName("demonstrativeType")
    val demonstrativeType: Long,

    @SerializedName("cyclothymicType")
    val cyclothymicType: Long,

    @SerializedName("anxiouslyFearfulType")
    val anxiouslyFearfulType: Long,

    @SerializedName("finishedAt")
    val finishedAt: String



)