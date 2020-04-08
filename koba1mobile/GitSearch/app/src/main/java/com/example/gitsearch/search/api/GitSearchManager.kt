package com.example.gitsearch.search.api

import com.example.gitsearch.constant.ApiUrl
import com.example.gitsearch.search.data.GitSearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitSearchManager {

    fun requestGitRepositories(query: String): Call<GitSearchResponse> {
        return Retrofit.Builder()
            .baseUrl(ApiUrl.GitHubUrl.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().run{
                create(GitHubService::class.java).listRepos(query)
            }
    }
}