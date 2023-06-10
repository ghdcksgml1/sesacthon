package com.haechi.sesacthon.pharmacy.model

import com.haechi.sesacthon.common.model.TimeZone
import com.haechi.sesacthon.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "PHARMACY")
class Pharmacy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH, CascadeType.MERGE])
    @JoinColumn(name = "user_id")
    val user: User?, // 담당 약사

    // Parameter
    val name: String, // 약국 이름
    val imageUrl: String? = null, // 약국 사진
    val address: String, // 도로명 주소
    val latitude: Double, // 위도
    val longitude: Double // 경도
) : TimeZone() {
    constructor() : this(
        user = null,
        name = "",
        imageUrl = "",
        address = "",
        latitude = 0.0,
        longitude = 0.0
    )
}