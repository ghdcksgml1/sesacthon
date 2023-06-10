package com.haechi.sesacthon.config.exception.article

import com.haechi.sesacthon.config.exception.ApiException

class ArticleNotFoundException : ApiException("Article을 찾지 못했습니다.") {
}