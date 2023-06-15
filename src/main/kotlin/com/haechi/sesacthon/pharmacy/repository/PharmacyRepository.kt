package com.haechi.sesacthon.pharmacy.repository

import com.haechi.sesacthon.pharmacy.model.Pharmacy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PharmacyRepository : JpaRepository<Pharmacy, Long>, PharmacyRepositoryCustom {
    // 이름, 위도, 경도로 Pharmacy 찾기
    fun findByNameAndLatitudeAndLongitude(name: String, latitude: Double, longitude: Double): Pharmacy?
    // 약사명으로 찾기
    @Query("select p from Pharmacy p join fetch p.user u where u.name = :name")
    fun findByPharmacyUser(name: String): Pharmacy?
}