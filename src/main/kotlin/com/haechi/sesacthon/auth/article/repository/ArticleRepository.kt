package com.haechi.sesacthon.auth.article.repository

import com.haechi.sesacthon.auth.article.model.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleRepository : JpaRepository<Article, Long>, ArticleRepositoryCustom {
    @Query("select a from Article a join fetch a.user u where a.id = :id")
    fun findByIdWithUser(id: Long): Article?
}