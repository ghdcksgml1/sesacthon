package com.haechi.sesacthon.publichealth

import com.haechi.sesacthon.common.aop.RoleCheck
import com.haechi.sesacthon.config.exception.publichealth.PublicHealthAlreadyExistException
import com.haechi.sesacthon.publichealth.dto.PublicHealthCreateRequestDto
import com.haechi.sesacthon.publichealth.dto.PublicHealthDefaultDto
import com.haechi.sesacthon.publichealth.model.PublicHealth
import com.haechi.sesacthon.publichealth.repository.PublicHealthRepository
import com.haechi.sesacthon.user.model.Role
import com.haechi.sesacthon.user.model.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class PublicHealthService(
    val publichealthRepository: PublicHealthRepository
) {
    @RoleCheck(role = Role.PUBLICHEALTH)
    fun create(user: User, requestDto: PublicHealthCreateRequestDto): Any? {
        val publichealth = publichealthRepository.findByNameAndLatitudeAndLongitude(requestDto.name, requestDto.latitude, requestDto.longitude)

        // 이미 보건소가 존재하면 오류 발생
        if (publichealth != null) {
            throw PublicHealthAlreadyExistException()
        }

        val savedPublichealth = publichealthRepository.saveAndFlush(PublicHealth(
            user = user,
            name = requestDto.name,
            imageUrl = requestDto.imageUrl,
            introduce = requestDto.introduce,
            address = requestDto.address,
            latitude = requestDto.latitude,
            longitude = requestDto.longitude
        ))

        return PublicHealthDefaultDto(
            user_name = user.name,
            user_role = user.role.toString(),

            publichealth_id = savedPublichealth.id,
            publichealth_name = savedPublichealth.name,
            publichealth_imageUrl = savedPublichealth.imageUrl,
            publichealth_introduce = savedPublichealth.introduce,
            publichealth_address = savedPublichealth.address,
            publichealth_latitude = savedPublichealth.latitude,
            publichealth_longitude = savedPublichealth.longitude
        )
    }

}