package com.haechi.sesacthon.auth.article

import com.haechi.sesacthon.user.model.User
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/article")
class ArticleController(
    val articleService: ArticleService
) {

    @Operation(summary = "기사 단건 조회")
    @GetMapping("/{article_id}")
    fun select(
        @PathVariable("article_id") article_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(articleService.select(article_id))
    }

    @Operation(summary = "기사 리스트 조회")
    @GetMapping("/")
    fun selectList(
        @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(articleService.selectList(pageable))
    }
}