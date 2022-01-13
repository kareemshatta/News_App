package com.kareem.data.models

import com.kareem.domain.mapper.Mapper
import com.kareem.domain.models.ArticleDataModel
import java.util.*

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Mapper<Article, ArticleDataModel> {

    override fun map(from: Article): ArticleDataModel = ArticleDataModel(
        id = UUID.randomUUID().toString(),
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}