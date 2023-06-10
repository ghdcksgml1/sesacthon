package com.haechi.sesacthon.pharmacy.dto.local

data class AddressResponse(
    val documents: List<AddressDocument>,
    val meta: AddressMeta
)

data class AddressDocument(
    val address: AddressInfo? = null,
    val address_name: String? = null,
    val address_type: String? = null,
    val x: String? = null,
    val y: String? = null
)

data class AddressInfo(
    val address_name: String? = null,
    val b_code: String? = null,
    val h_code: String? = null,
    val main_address_no: String? = null,
    val mountain_yn: String? = null,
    val region_1depth_name: String? = null,
    val region_2depth_name: String? = null,
    val region_3depth_h_name: String? = null,
    val region_3depth_name: String? = null,
    val sub_address_no: String? = null,
    val x: String? = null,
    val y: String? = null
)

data class AddressMeta(
    val is_end: Boolean? = null,
    val pageable_count: Int? = null,
    val total_count: Int? = null
)