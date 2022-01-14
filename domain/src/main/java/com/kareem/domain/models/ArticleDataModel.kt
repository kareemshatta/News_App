package com.kareem.domain.models

import java.io.Serializable

data class ArticleDataModel(
    val id: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val sourceName: String,
    val url: String,
    val urlToImage: String
) : Serializable
