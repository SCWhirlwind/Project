package com.example.projectmobile

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("box_art_url") val boxArtUrl: String,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)