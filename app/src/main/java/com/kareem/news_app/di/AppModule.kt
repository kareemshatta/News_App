package com.kareem.news_app.di

import com.kareem.domain.useCases.GetNewsUseCase
import com.kareem.news_app.view_models.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        GetNewsUseCase(get())
    }
    viewModel { NewsViewModel(get()) }
}