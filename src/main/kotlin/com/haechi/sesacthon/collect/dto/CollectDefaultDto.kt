package com.haechi.sesacthon.collect.dto

import com.haechi.sesacthon.collect.model.Collect
import java.time.LocalDateTime

data class CollectDefaultDto(

    // Foreign Key
    val pharmacy_name: String, // 요청 약국
    val publichealth_name: String, // 수거 보건소

    val collect_id: Long,
    val collect_status: String, // 수거 현황
    val collect_reservationDate: LocalDateTime, // 수거 날짜
    val collect_memo: String? = null // 메모
) {
    constructor(collect: Collect) : this(
        pharmacy_name = collect.pharmacy!!.name,
        publichealth_name = collect.publichealth!!.name,
        collect_id = collect.id!!,
        collect_status = collect.status.toString(),
        collect_reservationDate = collect.reservationDate!!,
        collect_memo = collect.memo
    )

}
