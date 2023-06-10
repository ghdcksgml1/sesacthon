package com.haechi.sesacthon.config.awsS3

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/s3")
class AwsS3Controller(
    val awsS3Service: AwsS3Service
) {

    @PostMapping("/")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<String> {
        return ResponseEntity.ok(awsS3Service.uploadImage(file))
    }
}