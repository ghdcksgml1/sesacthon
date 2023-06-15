package com.haechi.sesacthon.collect.dto

import java.time.LocalDateTime

data class CollectRequestDto(
    val publichealth_id: Long, // 요청 보건소
    val memo: String? = null
) {
}