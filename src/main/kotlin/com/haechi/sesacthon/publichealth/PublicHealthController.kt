package com.haechi.sesacthon.publichealth

import com.haechi.sesacthon.config.exception.publichealth.PublicHealthNotFoundException
import com.haechi.sesacthon.publichealth.dto.PublicHealthCreateRequestDto
import com.haechi.sesacthon.publichealth.dto.PublicHealthDefaultDto
import com.haechi.sesacthon.publichealth.repository.PublicHealthRepository
import com.haechi.sesacthon.user.model.User
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/publichealth")
class PublicHealthController(
    val publichealthService: PublicHealthService,
    val publichealthRepository: PublicHealthRepository
) {
    @Operation(summary = "보건소 아이디로 검색.")
    @GetMapping("/{publichealth_id}")
    fun select(
        @PathVariable("publichealth_id") publichealth_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(
            PublicHealthDefaultDto(
                publichealthRepository.findById(publichealth_id).orElseThrow {
                    throw PublicHealthNotFoundException()
                }
            )
        )
    }

    @Operation(summary = "보건소 리스트 가져오기.")
    @GetMapping("/list/")
    fun selectAll(): ResponseEntity<Any> {
        return ResponseEntity.ok(
            publichealthRepository.findAll().map { PublicHealthDefaultDto(it) }
        )
    }

    @Operation(summary = "보건소 생성하기. Role이 PUBLICHEALTH만 가능.")
    @PostMapping("/")
    fun create(
        @AuthenticationPrincipal user: User,
        @RequestBody requestDto: PublicHealthCreateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(publichealthService.create(user, requestDto))
    }
}