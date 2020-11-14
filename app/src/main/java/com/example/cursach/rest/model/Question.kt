package com.example.cursach.rest.model

data class Question(
    val id: Long,
    val question: String,
    val isAdd: Boolean,
    val questionTypeId: Long
)