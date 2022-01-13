package com.kareem.domain.useCases

import com.kareem.domain.repositories.NewsRepository
import com.kareem.domain.models.ArticleDataModel
import com.kareem.domain.result.Resource

class GetNewsUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(pageNumber: Int): Resource<MutableList<ArticleDataModel>?> {
        return try {
            val data = repository.getLatestNews(pageNumber)
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}