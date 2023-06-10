package com.haechi.sesacthon.pharmacy

import com.haechi.sesacthon.user.model.User
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/pharmacy")
class PharmacyController(
    val pharmacyService: PharmacyService
) {

    @Operation(summary = "약국을 등록하는 API 입니다. (약사만 사용 가능합니다. CHEMIST) file은 꼭 들어가지 않아도 됩니다.")
    @GetMapping("/enroll")
    fun enrollPharmacy(
        @AuthenticationPrincipal user: User,
        @RequestParam("file", required = false) file: MultipartFile?,
        @RequestParam("name") name: String,
        @RequestParam("address") address: String
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(pharmacyService.enroll(user, file, name, address))
    }

    @Operation(summary = "latitude와 longitude를 넘겨주면 5KM 반경의 약국을 리턴해줍니다.")
    @GetMapping("/around")
    fun aroundPharmacyList(
        @RequestParam("latitude") latitude: Double,
        @RequestParam("longitude") longitude: Double
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(pharmacyService.aroundPharmacyList(latitude, longitude))
    }

    @Operation(summary = "주소쳐서 위도, 경도 받기")
    @GetMapping("/search")
    fun search(
        @RequestParam("address") address: String
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(pharmacyService.localServer(address))
    }
}