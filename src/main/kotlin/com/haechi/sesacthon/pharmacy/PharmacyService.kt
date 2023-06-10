package com.haechi.sesacthon.pharmacy

import com.haechi.sesacthon.config.awsS3.AwsS3Service
import com.haechi.sesacthon.config.exception.local.LocalResponseException
import com.haechi.sesacthon.config.exception.pharmacy.PharmacyAlreadyExistException
import com.haechi.sesacthon.pharmacy.dto.PharmacyDefaultDto
import com.haechi.sesacthon.pharmacy.dto.local.AddressResponse
import com.haechi.sesacthon.pharmacy.model.Pharmacy
import com.haechi.sesacthon.pharmacy.repository.PharmacyRepository
import com.haechi.sesacthon.user.model.User
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import kotlin.math.cos

@Service
class PharmacyService(
    @Value("\${kakao.appKey}") val appKey: String,
    val restTemplate: RestTemplate,
    val awsS3Service: AwsS3Service,
    val pharmacyRepository: PharmacyRepository
) {

    @Transactional
    fun enroll(user: User, file: MultipartFile?, name: String, address: String): Any {

        val response = localServer(address).documents

        val latitude = response[0].x!!.toDouble()
        val longitude = response[0].y!!.toDouble()

        if (pharmacyRepository.findByNameAndLatitudeAndLongitude(name, latitude, longitude) != null) {
            throw PharmacyAlreadyExistException()
        }

        var image: String? = null
        if (file != null) {
            image = awsS3Service.uploadImage(file)
        }

         val savedPharmacy = pharmacyRepository.save(Pharmacy(
            user = user,
            name = name,
            imageUrl = image,
            address = address,
            latitude = latitude,
            longitude = longitude
        ))

        return "ok"
    }

    fun aroundPharmacyList(latitude: Double, longitude: Double): Any {
        val radius = 5.0 // km

        val latDelta = radius / 111.0 // 1도의 위도 변화에 대한 거리 (약 111km)
        val lonDelta = radius / (111.0 * cos(Math.toRadians(latitude))) // 1도의 경도 변화에 대한 거리

        val minLat = latitude - latDelta
        val maxLat = latitude + latDelta
        val minLon = longitude + lonDelta
        val maxLon = longitude - lonDelta

        println("${minLat} ${maxLat} ${minLon} ${maxLon}  ======= ${latitude} ${longitude}")

        return pharmacyRepository.findByLatitudeAndLongitude(minLat, maxLat, minLon, maxLon)
            .map{ PharmacyDefaultDto(it) }
    }

    fun localServer(address: String): AddressResponse {
        val header = HttpHeaders()
        header.set("Authorization", "KakaoAK " + appKey)

        val entity = HttpEntity("", header)

        val response = restTemplate.exchange(
            "https://dapi.kakao.com/v2/local/search/address.json?query=" + address,
            HttpMethod.GET,
            entity,
            AddressResponse::class.java
        ).body ?: throw LocalResponseException()

        return response
    }
}

