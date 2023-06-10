package com.haechi.sesacthon.config.exception.user

import com.haechi.sesacthon.config.exception.ApiException

class UserForbiddenException : ApiException("접근 권한이 없습니다.") {
}