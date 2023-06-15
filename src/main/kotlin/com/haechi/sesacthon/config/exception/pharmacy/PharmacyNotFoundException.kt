package com.haechi.sesacthon.config.exception.pharmacy

import com.haechi.sesacthon.config.exception.ApiException

class PharmacyNotFoundException : ApiException("약국 정보가 존재하지 않습니다.") {
}