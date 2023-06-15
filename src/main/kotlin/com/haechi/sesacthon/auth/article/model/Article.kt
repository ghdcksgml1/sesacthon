package com.haechi.sesacthon.auth.article.model

import com.haechi.sesacthon.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "ARTICLE")
class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH, CascadeType.MERGE])
    @JoinColumn(name = "user_id")
    val user: User?, // 게시물 작성자

    // Parameter
    val title: String, // 제목
    @Column(columnDefinition = "text")
    val content: String, // 내용
    val imageUrl: String?, // 대표 사진
) {
    constructor() : this(
        user = null,
        title = "",
        content = "",
        imageUrl = ""
    )

}