package com.kareem.news_app

import com.kareem.domain.models.ArticleDataModel
import com.kareem.domain.repositories.NewsRepository
import com.kareem.domain.result.Resource
import com.kareem.domain.useCases.GetNewsUseCase
import com.kareem.news_app.view_models.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
internal class NewsViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsUseCase: GetNewsUseCase
    private lateinit var repository: NewsRepository

    @Before
    fun init() {
        repository = object : NewsRepository {
            override suspend fun getLatestNews(pageNumber: Int): MutableList<ArticleDataModel>? {
                return mutableListOf()
            }
        }
        newsUseCase = GetNewsUseCase(repository)
        viewModel = NewsViewModel(newsUseCase, coroutinesTestRule.testDispatcher)
    }

    @Test
    fun `test clearReferences() behavior then validate properties change`() {
        viewModel.clearReferences()
        assertTrue(!viewModel.isLoading)
        assertTrue(viewModel.pageInfo.pageNumber == 1)
        assertTrue(viewModel.pageInfo.hasNext)
        assertTrue(viewModel.job.isCancelled)
    }

    @Test
    fun `test onLoadMore() then validate state will change to loading`() {
        viewModel.onLoadMore()
        assertTrue(viewModel.newsStateFlow.value is Resource.Loading)
    }

    @Test
    fun `test onLoadNews() with success results then validate result has state flow is success`() {
        runBlocking {
            viewModel.onLoadNews(
                Resource.Success(
                    mutableListOf()
                )
            )
        }
        assertTrue(viewModel.newsStateFlow.value is Resource.Success)
    }

    @Test
    fun `test onLoadNews() with error results then validate result has state flow is success`() {
        runBlocking {
            viewModel.onLoadNews(
                Resource.Error(
                    "Error"
                )
            )
        }
        assertTrue(viewModel.newsStateFlow.value is Resource.Error)
    }

    @Test
    fun `test onLoadNews() with success result then validate page info has next`() {
        val mockRepositoryObject = object : NewsRepository {
            override suspend fun getLatestNews(pageNumber: Int): MutableList<ArticleDataModel> {
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
        runBlocking {
            val response = newsUseCase(1)
            viewModel.onLoadNews(response)
            assertTrue(response is Resource.Success)
            assertTrue(viewModel.pageInfo.hasNext)
        }
    }
    @Test
    fun `test onLoadNews() with success result then validate page info not has next`() {
        val mockRepositoryObject = object : NewsRepository {
            override suspend fun getLatestNews(pageNumber: Int): MutableList<ArticleDataModel>? = null
        }
        val newsUseCase = GetNewsUseCase(mockRepositoryObject)
        runBlocking {
            val response = newsUseCase(1)
            viewModel.onLoadNews(response)
            assertTrue(response is Resource.Success)
            assertTrue(viewModel.pageInfo.hasNext.not())
        }
    }
}