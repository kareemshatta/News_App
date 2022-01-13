package com.kareem.data.di

import com.kareem.data.remote.ApiInterface
import com.kareem.data.repositories_imp.NewsRepositoryImp
import com.kareem.data.utils.Constants.Companion.BASE_URL
import com.kareem.domain.repositories.NewsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        getRetrofitInstance()
    }

    single {
        getApiInterface(get())
    }

    single<NewsRepository> {
        NewsRepositoryImp(get())
    }
}

fun getRetrofitInstance(): Retrofit {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}
fun getApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)