package com.haechi.sesacthon

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @Operation(hidden = true)
    @GetMapping("/")
    fun healthCheck() = "server Run."
}