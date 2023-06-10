package com.haechi.sesacthon.auth.article.repository

import com.haechi.sesacthon.auth.article.model.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ArticleRepositoryCustom {
    open fun findByList(pageable: Pageable): Page<Article>
}