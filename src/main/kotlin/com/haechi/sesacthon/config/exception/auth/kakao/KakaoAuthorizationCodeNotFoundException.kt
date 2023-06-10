package com.haechi.sesacthon.config.exception.auth.kakao

import com.haechi.sesacthon.config.exception.ApiException

class KakaoAuthorizationCodeNotFoundException
    : ApiException("AuthorizationCode를 찾을 수 없습니다.") {
}