package com.haechi.sesacthon.pharmacy.dto

import com.haechi.sesacthon.pharmacy.model.Pharmacy

data class PharmacyDefaultDto(
    val pharmacy_id: Long,
    val pharmacy_name: String, // 약국 이름
    val pharmacy_imageUrl: String? = null, // 약국 사진
    val pharmacy_address: String, // 도로명 주소
    val pharmacy_latitude: Double, // 위도
    val pharmacy_longitude: Double // 경도
) {
    constructor(pharmacy: Pharmacy) : this(
        pharmacy_id = pharmacy.id!!,
        pharmacy_name = pharmacy.name,
        pharmacy_imageUrl = pharmacy.imageUrl,
        pharmacy_address = pharmacy.address,
        pharmacy_latitude = pharmacy.latitude,
        pharmacy_longitude = pharmacy.longitude
    )

}