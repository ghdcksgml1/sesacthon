package com.haechi.sesacthon.collect.model

import com.haechi.sesacthon.common.model.TimeZone
import com.haechi.sesacthon.pharmacy.model.Pharmacy
import com.haechi.sesacthon.publichealth.model.PublicHealth
import com.haechi.sesacthon.user.model.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "COLLECT")
class Collect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH, CascadeType.MERGE])
    @JoinColumn(name = "pharmacy_id")
    val pharmacy: Pharmacy? = null, // 요청 약국
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH, CascadeType.MERGE])
    @JoinColumn(name = "publichealth_id")
    val publichealth: PublicHealth? = null, // 수거 보건소

    // Parameter
    @Enumerated(EnumType.STRING)
    var status: CollectType? = CollectType.NONE, // 수거 현황
    var reservationDate: LocalDateTime? = null, // 수거 날짜
    var memo: String? = null // 메모
) : TimeZone() {
    constructor() : this(
        status = CollectType.NONE,
        memo = ""
    )
}