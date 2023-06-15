package com.haechi.sesacthon.publichealth.repository

import com.haechi.sesacthon.publichealth.model.PublicHealth
import com.haechi.sesacthon.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PublicHealthRepository : JpaRepository<PublicHealth, Long> {
    fun findByNameAndLatitudeAndLongitude(name: String, latitude: Double, longitude: Double): PublicHealth?
    fun findByUser(user: User): PublicHealth?
}