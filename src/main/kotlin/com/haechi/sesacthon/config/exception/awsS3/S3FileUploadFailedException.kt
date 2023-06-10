package com.haechi.sesacthon.config.exception.awsS3

import com.haechi.sesacthon.config.exception.ApiException

class S3FileUploadFailedException : ApiException("파일 업로드에 실패하였습니다.") {
}