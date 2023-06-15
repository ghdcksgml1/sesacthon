package com.haechi.sesacthon.config.exception.publichealth

import com.haechi.sesacthon.config.exception.ApiException

class PublicHealthAlreadyExistException : ApiException("이미 존재하는 보건소입니다.") {
}