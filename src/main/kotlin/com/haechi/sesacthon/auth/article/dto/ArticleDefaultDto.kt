package com.haechi.sesacthon.auth.article.dto

import com.haechi.sesacthon.auth.article.model.Article

data class ArticleDefaultDto(
    val user_id: Long,
    val user_name: String,
    val user_role: String,
    val user_profileImageUrl: String? = null,

    val article_id: Long,
    val article_title: String,
    val article_content: String,
    val article_imageUrl: String? = null
) {
    constructor(article: Article) : this(
        user_id = article.user!!.id!!,
        user_name = article.user!!.name,
        user_role = article.user!!.role.toString(),
        user_profileImageUrl = article.user!!.profileImageUrl,
        article_id = article.id!!,
        article_title = article.title,
        article_content = article.content,
        article_imageUrl = article.imageUrl
    )
}