package com.haechi.sesacthon.config.exception.publichealth

import com.haechi.sesacthon.config.exception.ApiException

class PublicHealthNotFoundException : ApiException("보건소 정보를 찾지 못했습니다.") {
}