package com.example.projectmobile

import com.google.gson.annotations.SerializedName

data class Stream (
    @SerializedName("game_id")
    val gameId: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("started_at")
    val startedAt: String,
    @SerializedName("tag_ids")
    val tagIds: List<String>,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("viewer_count")
    val viewerCount: Int
)