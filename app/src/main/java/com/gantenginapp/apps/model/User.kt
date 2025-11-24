package com.gantenginapp.apps.model



data class User(
    val id : String? = null,
    val username: String,
    val password: String,
    val role :String,
    val email : String,
    val noHp : String
)