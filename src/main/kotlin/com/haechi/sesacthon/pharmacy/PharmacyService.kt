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
        val EARTH_RADIUS = 6371.0
        val radius = 1000.0 // 1km

        val latDelta = (1 / (EARTH_RADIUS * 1 * (Math.PI/ 180))) / 1000
        val lonDelta = (1 /(EARTH_RADIUS* 1 *(Math.PI/ 180)* Math.cos(Math.toRadians(latitude))))/ 1000

        val minLat = latitude - (radius * latDelta)
        val maxLat = latitude + (radius * latDelta)
        val minLon = longitude + (radius * lonDelta)
        val maxLon = longitude - (radius * lonDelta)

        println("${minLat} ${maxLat} ${minLon} ${maxLon}  ======= ${latitude} ${longitude}")

        return pharmacyRepository.findByLatitudeAndLongitude(minLat, maxLat, minLon, maxLon)
            .map{ PharmacyDefaultDto(it) }
    }

    fun findLocal(address: String): Any {
        return localServer(address).documents
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

