package com.haechi.sesacthon.publichealth.dto

import com.haechi.sesacthon.publichealth.model.PublicHealth

data class PublicHealthDefaultDto(
    val user_name: String,
    val user_role: String,

    val publichealth_id: Long?,
    val publichealth_name: String,
    val publichealth_imageUrl: String?,
    val publichealth_introduce: String?,
    val publichealth_address: String,
    val publichealth_latitude: Double,
    val publichealth_longitude: Double
) {
    constructor(publichealth: PublicHealth) : this(
        user_name = publichealth.user!!.name,
        user_role = publichealth.user!!.role.toString(),
        publichealth_id = publichealth.id,
        publichealth_name = publichealth.name,
        publichealth_imageUrl = publichealth.imageUrl,
        publichealth_introduce = publichealth.introduce,
        publichealth_address = publichealth.address,
        publichealth_latitude = publichealth.latitude,
        publichealth_longitude = publichealth.longitude
    )
}