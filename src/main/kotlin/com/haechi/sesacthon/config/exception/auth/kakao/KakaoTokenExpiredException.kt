package com.haechi.sesacthon.config.exception.auth.kakao

import com.haechi.sesacthon.config.exception.ApiException

class KakaoTokenExpiredException
    : ApiException("카카오 토큰이 만료되었습니다.") {
}