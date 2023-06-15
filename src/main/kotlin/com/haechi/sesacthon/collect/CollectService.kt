package com.haechi.sesacthon.collect

import com.haechi.sesacthon.collect.dto.CollectDefaultDto
import com.haechi.sesacthon.collect.dto.CollectRequestDto
import com.haechi.sesacthon.collect.model.Collect
import com.haechi.sesacthon.collect.model.CollectType
import com.haechi.sesacthon.collect.repository.CollectRepository
import com.haechi.sesacthon.common.aop.RoleCheck
import com.haechi.sesacthon.config.exception.collect.CollectNotFoundException
import com.haechi.sesacthon.config.exception.pharmacy.PharmacyNotFoundException
import com.haechi.sesacthon.config.exception.publichealth.PublicHealthNotFoundException
import com.haechi.sesacthon.config.exception.user.UserForbiddenException
import com.haechi.sesacthon.pharmacy.repository.PharmacyRepository
import com.haechi.sesacthon.publichealth.repository.PublicHealthRepository
import com.haechi.sesacthon.user.model.Role
import com.haechi.sesacthon.user.model.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class CollectService(
    val collectRepository: CollectRepository,
    val pharmacyRepository: PharmacyRepository,
    val publichealthRepository: PublicHealthRepository
) {
    fun select(user: User, collect_id: Long): Any {
        val userRole = user.role

        if (userRole.compareTo(Role.CHEMIST) != 0 && userRole.compareTo(Role.PUBLICHEALTH) != 0) {
            throw UserForbiddenException()
        }

        return CollectDefaultDto(
            collectRepository.findByIdWithAll(collect_id, user.id!!)
                ?: throw CollectNotFoundException()
        )
    }

    fun selectAll(user: User, memo: String): Any {
        val userRole = user.role

        val collect: MutableList<Collect> = when (userRole) {
            Role.CHEMIST -> collectRepository.findAllWithAllPharmacy(user.id!!, memo)
            Role.PUBLICHEALTH -> collectRepository.findAllWithAllPublicHealth(user.id!!, memo)
            else -> throw UserForbiddenException()
        }

        return collect.map { CollectDefaultDto(
            pharmacy_name = it.pharmacy!!.name,
            pharmacy_address = it.pharmacy!!.address,
            publichealth_name = it.publichealth!!.name,
            collect_id = it.id!!,
            collect_status = it.status.toString(),
            collect_reservationDate = it.reservationDate!!,
            collect_memo = it.memo
        ) }
    }

    @RoleCheck(role = Role.CHEMIST)
    fun request(user: User, requestDto: CollectRequestDto): Any {
        val pharmacy = pharmacyRepository.findByPharmacyUser(user.name)
            ?: throw PharmacyNotFoundException()

        val publichealth = publichealthRepository.findById(requestDto.publichealth_id).orElseThrow {
            throw PublicHealthNotFoundException()
        }

        return CollectDefaultDto(
            collectRepository.saveAndFlush(Collect(
                pharmacy = pharmacy,
                publichealth = publichealth,
                status = CollectType.ACCEPT,
                reservationDate = LocalDateTime.now().plusDays(2),
                memo = requestDto.memo
            ))
        )
    }

//    @RoleCheck(role = Role.PUBLICHEALTH)
//    fun accept(user: User, requestDto: CollectRequestDto): Any {
//        val publichealth = publichealthRepository.findByUser(user)
//            ?: throw PublicHealthNotFoundException()
//
//        return CollectDefaultDto(
//            collectRepository.saveAndFlush(Collect(
//                pharmacy = pharmacy,
//                publichealth = publichealth,
//                status = CollectType.WAITING,
//                reservationDate = requestDto.reservationDate,
//                memo = requestDto.memo
//            ))
//        )
//    }
}