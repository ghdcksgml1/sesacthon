package com.haechi.sesacthon.publichealth.model

import com.haechi.sesacthon.common.model.TimeZone
import com.haechi.sesacthon.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "PUBLICHEALTH")
class PublicHealth(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH, CascadeType.MERGE])
    @JoinColumn(name = "user_id")
    val user: User?, // 보건소 담당자

    // Parameter
    val name: String, // 보건소 이름
    var imageUrl: String? = null, // 보건소 사진
    @Column(columnDefinition = "text")
    var introduce: String? = null, // 보건소 소개
    val address: String, // 도로명 주소
    val latitude: Double, // 위도
    val longitude: Double // 경도
) : TimeZone() {
    constructor() : this(
        user = null,
        name = "",
        address = "",
        latitude = 0.0,
        longitude = 0.0
    )
}