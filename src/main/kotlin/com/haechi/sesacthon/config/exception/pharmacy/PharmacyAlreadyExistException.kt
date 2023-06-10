package com.haechi.sesacthon.config.exception.pharmacy

import com.haechi.sesacthon.config.exception.ApiException

class PharmacyAlreadyExistException : ApiException("동일한 약국정보가 존재합니다.") {
}