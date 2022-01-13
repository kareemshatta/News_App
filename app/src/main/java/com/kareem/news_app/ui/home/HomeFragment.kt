package com.kareem.news_app.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kareem.domain.result.Resource
import com.kareem.news_app.databinding.HomeFragmentBinding
import com.kareem.news_app.ui.home.epoxy_models.ArticleCardModel_
import com.kareem.news_app.view_models.NewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope {
    private val viewModel by viewModel<NewsViewModel>()
    private lateinit var binding: HomeFragmentBinding
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.articlesRecyclerView.withModels {
            launch(coroutineContext){
                viewModel.newsStateFlow.collect { result ->
                    when (result) {
                        is Resource.Idle -> {
                            binding.articlesSwipeToRefresh.isRefreshing = false
                        }
                        is Resource.Loading -> {
                            binding.articlesSwipeToRefresh.isRefreshing = true
                        }
                        is Resource.Error -> {
                            //handle error here
                        }
                        is Resource.Success -> {
                            result.data?.forEachIndexed{ index, articleModel ->
                                ArticleCardModel_()
                                    .id(index)
                                    .text(articleModel.title)
                            }
                            viewModel.newsStateFlow.emit(Resource.Idle())
                        }
                    }
                }
            }
        }

    }

}