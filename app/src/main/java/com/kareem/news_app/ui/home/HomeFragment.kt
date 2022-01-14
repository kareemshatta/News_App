package com.kareem.news_app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kareem.domain.models.ArticleDataModel
import com.kareem.domain.result.Resource
import com.kareem.news_app.databinding.HomeFragmentBinding
import com.kareem.news_app.ui.home.epoxy_models.articleCard
import com.kareem.news_app.view_models.NewsViewModel
import com.paginate.Paginate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope, SwipeRefreshLayout.OnRefreshListener {
    private val viewModel by viewModel<NewsViewModel>()
    private lateinit var binding: HomeFragmentBinding
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    private val fragmentTag = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildRecyclerViewItems(mutableListOf())
        setupSwipeToRefresh()
        handleRequestStates()
        loadNewsWithPagination()
    }

    private fun setupSwipeToRefresh() {
        binding.articlesSwipeToRefresh.apply {
            setOnRefreshListener(this@HomeFragment)
            setProgressViewEndTarget(false, 120)
        }
    }

    private fun loadNewsWithPagination() {
        Paginate.with(binding.articlesRecyclerView, viewModel)
            .setLoadingTriggerThreshold(2)
            .addLoadingListItem(false)
            .build()
    }

    private fun handleRequestStates() {
        launch(coroutineContext) {
            viewModel.newsStateFlow.collect { result ->
                when (result) {
                    is Resource.Idle -> {
                        binding.articlesSwipeToRefresh.isRefreshing = false
                    }
                    is Resource.Loading -> {
                        if (viewModel.pageInfo.pageNumber == 1) {
                            binding.articlesSwipeToRefresh.isRefreshing = true
                        }
                    }
                    is Resource.Error -> {
                        binding.networErrorLayout.root.visibility = View.VISIBLE
                        Log.d(fragmentTag, "handleRequestStates: ${result.message}")
                        viewModel.newsStateFlow.emit(Resource.Idle())
                    }
                    is Resource.Success -> {
                        result.data?.let {
                            buildRecyclerViewItems(it)
                        }
                        viewModel.newsStateFlow.emit(Resource.Idle())
                    }
                }
            }
        }
    }

    private fun buildRecyclerViewItems(articleModels: MutableList<ArticleDataModel>) {
        binding.articlesRecyclerView.withModels {
            articleModels.forEach { articleModel ->
                articleCard {
                    id(articleModel.id)
                    articleDataModel(articleModel)
                }
            }
        }
    }

    override fun onRefresh() {
        if (binding.networErrorLayout.root.isVisible) {
            binding.networErrorLayout.root.visibility = View.GONE
        }
        viewModel.resetPagination()
        loadNewsWithPagination()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }

}