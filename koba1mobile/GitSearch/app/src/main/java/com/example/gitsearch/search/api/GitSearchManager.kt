package com.example.gitsearch.search.api

import com.example.gitsearch.constant.ApiUrl
import com.example.gitsearch.search.data.GitSearchResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GitSearchManager {

    fun requestGitRepositories(query: String): Observable<GitSearchResponse> {
        return Retrofit.Builder()
            .baseUrl(ApiUrl.GitHubUrl.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build().run{
                create(GitHubService::class.java).listRepos(query)
            }
    }
}