package com.haechi.sesacthon.config.exception.collect

import com.haechi.sesacthon.config.exception.ApiException

class CollectNotFoundException : ApiException("Collect 정보를 찾지 못했습니다.") {
}