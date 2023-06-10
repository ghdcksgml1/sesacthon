package com.haechi.sesacthon.pharmacy.dto

import com.haechi.sesacthon.user.model.User
import jakarta.persistence.CascadeType
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

data class PharmacyEnrollResponseDto(
    val id: Long,

    // Parameter
    val name: String, // 약국 이름
    val imageUrl: String, // 약국 사진
    val address: String, // 도로명 주소
    val latitude: Double, // 위도
    val longitude: Double // 경도
) {
}