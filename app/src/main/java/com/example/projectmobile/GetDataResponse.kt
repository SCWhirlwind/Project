package com.example.projectmobile

import com.google.gson.annotations.SerializedName

data class GetDataResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("pagination")
    val pagination: Pagination
)