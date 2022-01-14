package com.kareem.news_app.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareem.domain.models.ArticleDataModel
import com.kareem.domain.result.Resource
import com.kareem.domain.useCases.GetNewsUseCase
import com.kareem.news_app.utils.PageInfoModel
import com.paginate.Paginate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel(), Paginate.Callbacks {

    private val job = Job()
    private var _pageInfo = PageInfoModel.init()
    val pageInfo get() = _pageInfo
    val newsStateFlow =
        MutableStateFlow<Resource<MutableList<ArticleDataModel>?>>(Resource.Idle())

    private suspend fun getLatestNews(): Resource<MutableList<ArticleDataModel>?> {
        return withContext(Dispatchers.IO) {
            getNewsUseCase(_pageInfo.pageNumber)
        }
    }


    fun resetPagination() {
        _pageInfo = PageInfoModel.init()
    }

    private suspend fun onLoadNews(result: Resource<MutableList<ArticleDataModel>?>) {
        newsStateFlow.emit(result)
        if (result is Resource.Success) {
            _pageInfo.pageNumber = _pageInfo.pageNumber.plus(1)
            _pageInfo.hasNext = result.data.isNullOrEmpty().not()
        } else {
            _pageInfo.hasNext = false
        }
    }

    override fun onLoadMore() {
        viewModelScope.launch {
            this@NewsViewModel.newsStateFlow.emit(Resource.Loading())
            val response = getLatestNews()
            onLoadNews(response)
        }
    }

    override fun isLoading(): Boolean = this.newsStateFlow.value is Resource.Loading
    override fun hasLoadedAllItems(): Boolean = !_pageInfo.hasNext

    override fun onCleared() {
        super.onCleared()
        clearReferences()
    }

    private fun clearReferences() {
        resetPagination()
        job.cancel()
    }
}