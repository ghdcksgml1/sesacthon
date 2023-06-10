package com.haechi.sesacthon.auth.controller


import com.haechi.sesacthon.auth.dto.AuthRegisterRequest
import com.haechi.sesacthon.auth.dto.AuthResponse
import com.haechi.sesacthon.auth.dto.AuthValidDetailResponse
import com.haechi.sesacthon.auth.dto.AuthValidResponse
import com.haechi.sesacthon.auth.dto.kakao.KakaoLoginPageResponse
import com.haechi.sesacthon.auth.service.AuthService
import com.haechi.sesacthon.user.model.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    // OAuth 2.0 Get kakao loginPage
    @Operation(summary = "OAuth 2.0 Get kakao loginPage", description = "로그인 페이지 url 얻기")
    @GetMapping("/kakao/loginPage")
    fun kakaoLoginPage(): ResponseEntity<KakaoLoginPageResponse> {
        return ResponseEntity.ok(authService.kakaoLoginPage())
    }

    // OAuth 2.0 Token Provider
    @Operation(summary = "OAuth 2.0 Token Provide", description = "카카오 로그인 후 롤백을 받는 api [프론트에서 신경 쓸 필요 없음]")
    @GetMapping("/kakao/callback")
    fun kakaoTokenProvider(
        @Parameter(name = "code", required = true, example = "asklcjkdlsaasfjklasjfklsafa") @RequestParam("code") code: String
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(authService.kakaoTokenProvider(code))
    }

    @Operation(summary = "계정 생성 마지막 단계 (성공 시 UNAUTH => USER, CHEMIST, PUBLICHEALTH | 유저, 약사, 보건소)", description = "폰번호만 보내주시면 돼요!")
    @PostMapping("/register")
    fun register(
        @RequestBody requestDto: AuthRegisterRequest
    ): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.register(requestDto))
    }

    @Operation(summary = "계정의 간단한 정보를 가져오는 API", description = "Authorization: Bearer \${token}헤더를 보내면 된다.")
    @GetMapping("/valid")
    fun valid(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<AuthValidResponse> {
        return ResponseEntity.ok(
            AuthValidResponse(
                user_id = user.id!!,
                user_name = user.name,
                user_role = user.role.toString(),
                user_profileImageUrl = user.profileImageUrl
            )
        )
    }

    @Operation(summary = "계정의 민감한 정보까지 가져오는 API", description = "Authorization: Bearer \${token}헤더를 보내면 된다.")
    @GetMapping("/valid/detail")
    fun validDetail(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<AuthValidDetailResponse> {
        return ResponseEntity.ok(
            AuthValidDetailResponse(
                user_id = user.id!!,
                user_platformId = user.platformId,
                user_name = user.name,
                user_role = user.role.toString(),
                user_profileImageUrl = user.profileImageUrl,
                user_phoneNumber = user.phoneNumber,
            )
        )
    }

    @GetMapping("/USER")
    fun testUser() = ResponseEntity.ok(authService.testUser())

    @GetMapping("/CHEMIST")
    fun testChemist() = ResponseEntity.ok(authService.testChemist())

    @GetMapping("/PUBLICHEALTH")
    fun testPublichealth() = ResponseEntity.ok(authService.testPublichealth())
}