package com.haechi.sesacthon.collect

import com.haechi.sesacthon.collect.dto.CollectRequestDto
import com.haechi.sesacthon.user.model.User
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/collect")
class CollectController(
    val collectService: CollectService
) {
    @Operation(summary = "collect 상세 조회 CHEMIST, PUBLICHEALTH만 가능")
    @GetMapping("/{collect_id}")
    fun select(
        @AuthenticationPrincipal user: User,
        @PathVariable("collect_id") collect_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(collectService.select(user, collect_id))
    }

    @Operation(summary = "collect 리스트 조회 (COLLECT 수거요청, REQUEST 수거통요청) CHEMIST, PUBLICHEALTH만 가능")
    @GetMapping("/list/{memo}")
    fun select(
        @AuthenticationPrincipal user: User,
        @PathVariable("memo") memo: String
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(collectService.selectAll(user, memo))
    }

    @Operation(summary = "약사가 보건소에게 수거 요청(COLLECT 수거요청, REQUEST 수거통요청), CHEMIST만 가능")
    @PostMapping("/request")
    fun request(
        @AuthenticationPrincipal user: User,
        @RequestBody requestDto: CollectRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(collectService.request(user, requestDto))
    }
}