package com.haechi.sesacthon.auth.service

import com.haechi.sesacthon.auth.dto.AuthRegisterRequest
import com.haechi.sesacthon.auth.dto.AuthRegisterResponse
import com.haechi.sesacthon.auth.dto.AuthResponse
import com.haechi.sesacthon.config.exception.auth.kakao.KakaoAuthorizationCodeNotFoundException
import com.haechi.sesacthon.config.exception.auth.kakao.KakaoTokenExpiredException
import com.haechi.sesacthon.config.exception.user.UserNotFoundException
import com.haechi.sesacthon.config.jwt.JwtService
import com.haechi.sesacthon.user.UserRepository
import com.haechi.sesacthon.user.model.Role
import com.haechi.sesacthon.user.model.User
import com.haechi.sesacthon.auth.dto.kakao.*
import com.haechi.sesacthon.utils.PhoneNumberValid
import jakarta.transaction.Transactional
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val kakaoValueBuilder: KakaoValueBuilder,
    private val restTemplate: RestTemplate
) {

    fun kakaoLoginPage(): KakaoLoginPageResponse {
        return kakaoValueBuilder.kakaoLoginPageResponse()
    }

    // 토큰 발급 시스템
    @Transactional
    fun kakaoTokenProvider(code: String): Any {
        // Header 설정
        val tokenRequestHeader = HttpHeaders()
        tokenRequestHeader.contentType = MediaType.APPLICATION_FORM_URLENCODED

        // kakaoTokenRequest 객체 생성
        val kakaoTokenRequest = kakaoValueBuilder.kakaoTokenRequest(code)

        val kakaoTokenResponse = restTemplate.postForObject(
            "https://kauth.kakao.com/oauth/token",
            HttpEntity(KakaoTokenRequest.of(kakaoTokenRequest), tokenRequestHeader),
            KakaoTokenResponse::class.java
        ) ?: throw KakaoAuthorizationCodeNotFoundException()

        // Header 설정
        val userInfoHeader = HttpHeaders()
        userInfoHeader.set("Authorization", "Bearer " + kakaoTokenResponse.access_token)

        val entity = HttpEntity("", userInfoHeader)

        val kakaoUserInfo = restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET,
            entity,
            KakaoUserInfoResponse::class.java
        ).body ?: throw KakaoTokenExpiredException()

        var email = kakaoUserInfo.kakao_account.email

        // User 테이블에서 platformId를 찾는다.
        // 만약 이미 존재한다면 user정보를 가져오고 jwt토큰 생성
        // user 정보가 존재하지 않는다면, user정보를 등록하고 jwt토큰 생성

        var user = userRepository.findByEmail(email)

        if (user == null) { // 회원가입
            user = userRepository.saveAndFlush(
                User(
                    platformId = passwordEncoder.encode(kakaoUserInfo.id),
                    platformType = "KAKAO",
                    name = kakaoUserInfo.kakao_account.profile.nickname,
                    role = Role.USER,
                    email = email,
                    phoneNumber = "",
                    profileImageUrl = kakaoUserInfo.kakao_account.profile.profile_image_url
                )
            )

            return AuthRegisterResponse(
                platformId = user.platformId,
                platformType = user.platformType
            )
        } else  { // 닉네임, 프로필 사진 업데이트
            user.name = kakaoUserInfo.kakao_account.profile.nickname

            user = userRepository.save(user)
        }

        val tokenHashMap = HashMap<String, String>()
        tokenHashMap["id"] = user.id.toString()
        tokenHashMap["role"] = user.role.toString()
        val jwtToken = jwtService.generateToken(tokenHashMap, user)

        return AuthResponse(jwtToken)
    }

    @Transactional
    fun register(requestDto: AuthRegisterRequest): AuthResponse {

        val user = userRepository.findByPlatformId(requestDto.platformId) ?: throw UserNotFoundException()
        user.phoneNumber = PhoneNumberValid.valid(requestDto.phoneNumber)
        val savedUser = userRepository.saveAndFlush(user)

        val tokenHashMap = HashMap<String, String>()
        tokenHashMap["id"] = user.id.toString()
        tokenHashMap["role"] = user.role.toString()
        val jwtToken = jwtService.generateToken(tokenHashMap, savedUser)

        return AuthResponse(jwtToken)
    }

    @Transactional
    fun testUser(): AuthResponse {
        val user = userRepository.findByEmail("test1@test.com")
            ?:userRepository.save(User(
                platformId = "abcdefg",
                platformType = "KAKAO",
                role = Role.USER,
                name = "테스트 유저계정",
                email = "test1@test.com",
                phoneNumber = "010-0000-0000",
                profileImageUrl = "http://k.kakaocdn.net/dn/bsKq6I/btr5E9S5jol/ABnBzO97fDIG8knP5hUoh1/img_640x640.jpg",
            ))

        // jwt 토큰 발급
        val tokenHashMap = HashMap<String, String>()
        tokenHashMap["id"] = user.id.toString()
        tokenHashMap["role"] = user.role.toString()
        val jwtToken = jwtService.generateToken(tokenHashMap, user)

        // 회원가입 결과 반환
        return AuthResponse(jwtToken)
    }

    @Transactional
    fun testChemist(): AuthResponse {
        val user = userRepository.findByEmail("test2@test.com")
            ?:userRepository.save(User(
                platformId = "abcdefg",
                platformType = "KAKAO",
                role = Role.CHEMIST,
                name = "테스트 약사계정",
                email = "test2@test.com",
                phoneNumber = "010-0000-0000",
                profileImageUrl = "http://k.kakaocdn.net/dn/bsKq6I/btr5E9S5jol/ABnBzO97fDIG8knP5hUoh1/img_640x640.jpg",
            ))

        // jwt 토큰 발급
        val tokenHashMap = HashMap<String, String>()
        tokenHashMap["id"] = user.id.toString()
        tokenHashMap["role"] = user.role.toString()
        val jwtToken = jwtService.generateToken(tokenHashMap, user)

        // 회원가입 결과 반환
        return AuthResponse(jwtToken)
    }

    @Transactional
    fun testPublichealth(): AuthResponse {
        val user = userRepository.findByEmail("test3@test.com")
            ?:userRepository.save(User(
                platformId = "abcdefg",
                platformType = "KAKAO",
                role = Role.PUBLICHEALTH,
                name = "테스트 보건소계정",
                email = "test3@test.com",
                phoneNumber = "010-0000-0000",
                profileImageUrl = "http://k.kakaocdn.net/dn/bsKq6I/btr5E9S5jol/ABnBzO97fDIG8knP5hUoh1/img_640x640.jpg",
            ))

        // jwt 토큰 발급
        val tokenHashMap = HashMap<String, String>()
        tokenHashMap["id"] = user.id.toString()
        tokenHashMap["role"] = user.role.toString()
        val jwtToken = jwtService.generateToken(tokenHashMap, user)

        // 회원가입 결과 반환
        return AuthResponse(jwtToken)
    }
}
