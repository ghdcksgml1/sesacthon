package com.haechi.sesacthon.user

import com.haechi.sesacthon.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?
    fun findByPlatformId(platformId: String): User?
}