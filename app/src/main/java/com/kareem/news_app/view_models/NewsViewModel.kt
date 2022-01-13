package com.kareem.news_app.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareem.domain.models.ArticleDataModel
import com.kareem.domain.result.Resource
import com.kareem.domain.useCases.GetNewsUseCase
import com.kareem.news_app.utils.PageInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val job = Job()
    private var pageInfo = PageInfoModel.init()
    private var _newsStateFlow =
        MutableStateFlow<Resource<MutableList<ArticleDataModel>?>>(Resource.Idle())
    val newsStateFlow get() = _newsStateFlow

    private suspend fun getLatestNews(): Resource<MutableList<ArticleDataModel>?> {
        return withContext(Dispatchers.IO) {
            getNewsUseCase(pageInfo.pageNumber)
        }
    }

    fun getBreakingNews() = viewModelScope.launch {
        _newsStateFlow.emit(Resource.Loading())
        val response = getNewsUseCase(pageInfo.pageNumber).data
        _newsStateFlow.emit(Resource.Success(response))
    }
    override fun onCleared() {
        super.onCleared()
        clearReferences()
    }

    private fun clearReferences() {
        resetPagination()
        job.cancel()
    }

    private fun resetPagination() {
        pageInfo = PageInfoModel.init()
    }
}