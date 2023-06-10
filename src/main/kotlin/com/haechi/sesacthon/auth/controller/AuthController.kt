package com.haechi.sesacthon.auth.controller


import com.haechi.sesacthon.auth.dto.AuthRegisterRequest
import com.haechi.sesacthon.auth.dto.AuthResponse
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

    @PostMapping("/register")
    fun register(
        @RequestBody requestDto: AuthRegisterRequest
    ): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.register(requestDto))
    }

    @GetMapping("/valid")
    fun isValidToken(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<AuthValidResponse> {
        return ResponseEntity.ok(
            AuthValidResponse(
                id = user.id!!,
                email = user.nickName,
                company = user.company
            )
        )
    }
}