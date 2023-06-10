package com.haechi.sesacthon.auth.article.repository

import com.haechi.sesacthon.auth.article.model.Article
import com.haechi.sesacthon.auth.article.model.QArticle.Companion.article
import com.haechi.sesacthon.user.model.QUser.Companion.user
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class ArticleRepositoryCustomImpl(
    val queryFactory: JPAQueryFactory
): ArticleRepositoryCustom {

    override fun findByList(pageable: Pageable): Page<Article> {
        val query = queryFactory
            .selectFrom(article)
            .join(article.user, user)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(article.id.desc())
            .fetchResults()

        return PageImpl(query.results, pageable, query.total)
    }
}