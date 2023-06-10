package com.haechi.sesacthon.auth.dto

data class AuthValidDetailResponse(
    val user_id: Long,
    val user_platformId: String,
    val user_name: String,
    val user_profileImageUrl: String,
    val user_role: String,
    val user_phoneNumber: String
) {
}