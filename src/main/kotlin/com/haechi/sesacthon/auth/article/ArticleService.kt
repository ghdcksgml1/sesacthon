package com.haechi.sesacthon.auth.article

import com.haechi.sesacthon.auth.article.dto.ArticleDefaultDto
import com.haechi.sesacthon.auth.article.repository.ArticleRepository
import com.haechi.sesacthon.config.exception.article.ArticleNotFoundException
import com.haechi.sesacthon.user.model.User
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ArticleService(
    val articleRepository: ArticleRepository
) {

    fun select(article_id: Long): Any {
        val article = articleRepository.findByIdWithUser(article_id)
            ?: throw ArticleNotFoundException()

        return ArticleDefaultDto(article)
    }

    fun selectList(pageable: Pageable): Any {
        return articleRepository.findByList(pageable)
            .map { ArticleDefaultDto(it) }
    }
}