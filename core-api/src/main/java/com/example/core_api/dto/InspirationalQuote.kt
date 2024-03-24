package com.example.core_api.dto

import com.google.gson.annotations.SerializedName

data class InspirationalQuote(
    @SerializedName("text")
    val text: String,

    @SerializedName("author")
    val author: String
)