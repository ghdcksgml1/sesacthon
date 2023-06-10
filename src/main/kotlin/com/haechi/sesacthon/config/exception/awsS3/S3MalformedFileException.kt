package com.haechi.sesacthon.config.exception.awsS3

import com.haechi.sesacthon.config.exception.ApiException

class S3MalformedFileException : ApiException("잘못된 형식의 파일 입니다.") {
}