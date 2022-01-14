package com.kareem.data.remote

import com.kareem.data.models.NewsResponse
import com.kareem.data.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("v2/everything")
    suspend fun getLatestNews(
        @Query("q")
        company: String = "apple",
        @Query("sortBy")
        sortedBy: String = "popularity",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}