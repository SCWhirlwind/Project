package com.example.projectmobile

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("cursor") val cursor: String
)