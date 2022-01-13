package com.kareem.data.repositories_imp

import com.kareem.data.remote.ApiInterface
import com.kareem.domain.models.ArticleDataModel
import com.kareem.domain.repositories.NewsRepository

class NewsRepositoryImp(private val service: ApiInterface) : NewsRepository {
    override suspend fun getLatestNews(pageNumber: Int): MutableList<ArticleDataModel>? =
        service.getLatestNews(pageNumber = pageNumber).body()?.articles
            ?.map { it.map(it) }?.toMutableList()
}