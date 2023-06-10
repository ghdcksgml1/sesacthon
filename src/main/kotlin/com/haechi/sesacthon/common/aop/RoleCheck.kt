package com.haechi.sesacthon.common.aop

import com.haechi.sesacthon.user.model.Role
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
annotation class RoleCheck(
    val role: Role = Role.USER
) {
}
