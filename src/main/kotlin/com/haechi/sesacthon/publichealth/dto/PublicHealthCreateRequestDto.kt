package com.haechi.sesacthon.publichealth.dto

import jakarta.persistence.Column

data class PublicHealthCreateRequestDto(
    val name: String, // 보건소 이름
    val imageUrl: String? = null, // 보건소 사진
    val introduce: String? = null, // 보건소 소개
    val address: String, // 도로명 주소
    val latitude: Double, // 위도
    val longitude: Double // 경도
)
