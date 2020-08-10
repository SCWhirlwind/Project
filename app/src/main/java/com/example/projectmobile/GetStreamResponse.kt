package com.example.projectmobile

import com.google.gson.annotations.SerializedName

data class GetStreamResponse(
    @SerializedName("data")
    val `stream`: List<Stream>,
    @SerializedName("pagination")
    val pagination: Pagination
)