package com.example.projectmobile


data class User(
    val id: String?,
    val username: String,
    val email: String,
    val password: String,
    val likes: List<Int>
)
