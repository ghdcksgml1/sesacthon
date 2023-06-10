package com.haechi.sesacthon.config.awsS3

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/s3")
class AwsS3Controller(
    val awsS3Service: AwsS3Service
) {

    @Operation(summary = "이미지 생성기 (무분별한 사용은 하지 말아주세요. 제 돈이 나갑니다.ㅜㅜ")
    @PostMapping("/")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<String> {
        return ResponseEntity.ok(awsS3Service.uploadImage(file))
    }
}