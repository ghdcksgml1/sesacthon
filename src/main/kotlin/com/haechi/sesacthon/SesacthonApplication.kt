package com.haechi.sesacthon

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info= Info(title = "새싹톤 해치워죠 API 명세서"))
class SesacthonApplication

fun main(args: Array<String>) {
	runApplication<SesacthonApplication>(*args)
}
