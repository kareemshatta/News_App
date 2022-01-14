package com.kareem.news_app;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import kotlinx.coroutines.ExperimentalCoroutinesApi;

class NewsViewModelTest {
//    @ExperimentalCoroutinesApi
//
//    private lateinit var viewModel: ArticlesFragmentViewModel
//    private lateinit var useCase: GetArticlesUseCase
//    private lateinit var repository: ArticleRepository
//
//    @ExperimentalCoroutinesApi
//    @Before
//    fun setup() {
//        repository = object : ArticleRepository {
//            override suspend fun getArticles(pageNumber: Int): MutableList<ArticleDataModel> =
//                mutableListOf()
//        }
//        useCase = GetArticlesUseCase(repository)
//        viewModel = ArticlesFragmentViewModel(
//            useCase,
//            coroutinesTestRule.testCoroutineDispatcher
//        )
//    }
//
//    @Test
//    fun `test clear() then validate`() {
//        viewModel.clear()
//        assertTrue(viewModel.job.isCancelled)
//        assertTrue(!viewModel.isLoadingCheck)
//        assertTrue(viewModel.pageInfo.pageNumber == 1)
//        assertTrue(viewModel.pageInfo.hasNext)
//    }
//
//    @Test
//    fun `test onLoadMore() when page number is one expected result flow is Loading state`() {
//        viewModel.onLoadMore()
//        assertTrue(viewModel.articlesFlowObserver.value is ResultModel.Loading)
//    }
//
//    @Test
//    fun `test onLoadMore() when page number is greater than one expected result is not Loading state`() {
//        viewModel.pageInfo.pageNumber = 2
//        viewModel.onLoadMore()
//        assertTrue(viewModel.articlesFlowObserver.value !is ResultModel.Loading)
//    }
//
//    @Test
//    fun `test onLoadArticles() with success results expected result flow is Success state`() {
//        runBlocking {
//            viewModel.onLoadArticles(
//                ResultModel.Success(
//                    mutableListOf()
//                )
//            )
//        }
//
//        assertTrue(viewModel.articlesFlowObserver.value is ResultModel.Success)
//    }
//
//    @Test
//    fun `test onLoadArticles() with error results expected result flow is Error state`() {
//        runBlocking {
//            viewModel.onLoadArticles(
//                ResultModel.Error(
//                    null
//                )
//            )
//        }
//
//        assertTrue(
//            viewModel.articlesFlowObserver.value !is ResultModel.Success &&
//                    viewModel.articlesFlowObserver.value is ResultModel.Error
//        )
//    }
//
//    @Test
//    fun `test onLoadArticles() with success result and null data then page info has no next`() {
//        repository = object : ArticleRepository {
//            override suspend fun getArticles(pageNumber: Int): MutableList<ArticleDataModel>? = null
//        }
//
//        useCase = GetArticlesUseCase(repository)
//        runBlocking {
//            val response = useCase(1)
//            viewModel.onLoadArticles(response)
//            assertTrue(response is ResultModel.Success)
//            assertTrue(!viewModel.pageInfo.hasNext)
//        }
//    }
//
//    @Test
//    fun `test onLoadArticles() with success result and empty list then page info has no next`() {
//        useCase = GetArticlesUseCase(repository)
//        runBlocking {
//            val response = useCase(1)
//            viewModel.onLoadArticles(response)
//            assertTrue(response is ResultModel.Success)
//            assertTrue(!viewModel.pageInfo.hasNext)
//        }
//    }
//
//    @Test
//    fun `test onLoadArticles() with success result and not empty list then page info has next`() {
//        repository = object : ArticleRepository {
//            override suspend fun getArticles(pageNumber: Int): MutableList<ArticleDataModel> =
//                mutableListOf(
//                    ArticleDataModel(
//                        id = "1",
//                        "author1",
//                        content = "content1",
//                        description = "description1",
//                        publishedAt = "1234",
//                        title = "title1",
//                        "url1",
//                        "urlImage1"
//                    )
//                )
//        }
//        useCase = GetArticlesUseCase(repository)
//        runBlocking {
//            val response = useCase(1)
//            viewModel.onLoadArticles(response)
//            assertTrue(response is ResultModel.Success)
//            assertTrue(viewModel.pageInfo.hasNext)
//        }
    }

