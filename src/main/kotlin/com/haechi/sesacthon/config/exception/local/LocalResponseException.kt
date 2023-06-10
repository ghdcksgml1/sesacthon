package com.haechi.sesacthon.config.exception.local

import com.haechi.sesacthon.config.exception.ApiException

class LocalResponseException : ApiException("주소를 불러오는데 오류가 발생했습니다.") {
}