package com.kareem.domain.repositories

import com.kareem.domain.models.ArticleDataModel

interface NewsRepository {
   suspend fun getLatestNews(pageNumber:Int): MutableList<ArticleDataModel>?
}