package com.haechi.sesacthon.user.model

import com.haechi.sesacthon.common.model.TimeZone
import com.haechi.sesacthon.user.model.Role
import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "USER")
class User(
    @Id @GeneratedValue
    val id: Long? = null,

    // Parameter
    val platformId: String,
    val platformType: String,
    @Enumerated(EnumType.STRING)
    var role: Role,
    var name: String,
    @Column(unique = true)
    val email: String,
    var phoneNumber: String
) : UserDetails, TimeZone() {

    constructor() : this(
        id = null,
        platformId = "",
        platformType = "",
        role = Role.USER,
        name = "",
        email = "",
        phoneNumber = ""
            ) // NoArgsConstructor

    // UserDetails Implements
    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return platformId
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
    // ====
}