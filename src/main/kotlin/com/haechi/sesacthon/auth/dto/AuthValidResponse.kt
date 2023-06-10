package com.haechi.sesacthon.auth.dto

data class AuthValidResponse(
    val user_id: Long,
    val user_name: String,
    val user_role: String,
    val user_profileImageUrl: String?,
) {
}