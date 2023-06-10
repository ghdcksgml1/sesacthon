package com.haechi.sesacthon.auth.dto

data class AuthRegisterRequest(
    val platformId: String,
    val platformType: String,
    val phoneNumber: String
)