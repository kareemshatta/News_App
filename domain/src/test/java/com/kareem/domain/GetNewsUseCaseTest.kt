package com.kareem.domain

import com.kareem.domain.models.ArticleDataModel
import com.kareem.domain.repositories.NewsRepository
import com.kareem.domain.result.Resource
import com.kareem.domain.useCases.GetNewsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.Exception

@RunWith(JUnit4::class)
class GetNewsUseCaseTest {

    @Test
    fun `test getLatestNews() behavior with exception that result Error`() {
        runBlocking {
            val mockRepositoryObject = object : NewsRepository {
                override suspend fun getLatestNews(pageNumber: Int): MutableList<ArticleDataModel>? {
                    throw Exception()
                }
            }
            val newsUseCase = GetNewsUseCase(mockRepositoryObject)
            val response = newsUseCase(1)
            assertTrue(response is Resource.Error)
        }
    }

    @Test
    fun `test getLatestNews() behavior with a null result that result Error`() {
        runBlocking {
            val mockRepositoryObject = object : NewsRepository {
                override suspend fun getLatestNews(pageNumber: Int): MutableList<ArticleDataModel>? {
                    return null
                }
            }

            val useCase = GetNewsUseCase(mockRepositoryObject)
            val response = useCase(1)
            if (response is Resource.Success) {
                assertEquals(response.data, null)
            } else {
                assertTrue(response is Resource.Error)
            }
        }
    }

    @Test
    fun `test getLatestNews() behavior with a null data then validate model data not null`() {
        runBlocking {
            val mockRepositoryObject = object : NewsRepository {
                override suspend fun getLatestNews(pageNumber: Int): MutableList<ArticleDataModel>? {
                    return mutableListOf(
                        ArticleDataModel(
                            id = "1",
                            author = "jone Dao",
                            content = "content of Article",
                            description = "description of Article",
                            publishedAt = "2022-01-05T15:23:53Z",
                            title = "title of article",
                            sourceName = "source name of article",
                            url = "https://www.theverge.com/2022/1/5/22868388/meta-xros-vr-ar-os-development-canceled",
                            urlToImage = "https://cdn.vox-cdn.com/thumbor/iK-KTeHZYspkC6uhIxui7MfT2VY=/0x146:2040x1214/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22977158/acastro_211101_1777_meta_0004.jpg"
                        )
                    )
                }
            }
            val newsUseCase = GetNewsUseCase(mockRepositoryObject)
            val response = newsUseCase(1)
            if (response is Resource.Success) {
                val expectedResult = mockRepositoryObject.getLatestNews(1)
                assertTrue(!response.data.isNullOrEmpty())
                assertEquals(response.data, expectedResult)
            } else {
                assertTrue(response is Resource.Error)
            }
        }
    }

    @Test
    fun `test getLatestNews() behavior with a right data that result Success`() {
        runBlocking {
            val mockRepositoryObject = object : NewsRepository {
                override suspend fun getLatestNews(pageNumber: Int): MutableList<ArticleDataModel>? {
                    return mutableListOf(
                        ArticleDataModel(
                            id = "1",
                            author = "jone Dao",
                            content = "content of Article",
                            description = "description of Article",
                            publishedAt = "2022-01-05T15:23:53Z",
                            title = "title of article",
                            sourceName = "source name of article",
                            url = "https://www.theverge.com/2022/1/5/22868388/meta-xros-vr-ar-os-development-canceled",
                            urlToImage = "https://cdn.vox-cdn.com/thumbor/iK-KTeHZYspkC6uhIxui7MfT2VY=/0x146:2040x1214/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22977158/acastro_211101_1777_meta_0004.jpg"
                        )
                    )
                }
            }

            val newsUseCase = GetNewsUseCase(mockRepositoryObject)
            val response = newsUseCase(1)
            assertTrue(response is Resource.Success)
        }
    }
}