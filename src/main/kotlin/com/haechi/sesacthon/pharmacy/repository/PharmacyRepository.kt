package com.haechi.sesacthon.pharmacy.repository

import com.haechi.sesacthon.pharmacy.model.Pharmacy
import org.springframework.data.jpa.repository.JpaRepository

interface PharmacyRepository : JpaRepository<Pharmacy, Long>, PharmacyRepositoryCustom {
    // 이름, 위도, 경도로 Pharmacy 찾기
    fun findByNameAndLatitudeAndLongitude(name: String, latitude: Double, longitude: Double): Pharmacy?
}