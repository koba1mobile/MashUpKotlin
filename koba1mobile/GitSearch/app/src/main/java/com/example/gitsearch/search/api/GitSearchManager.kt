package com.example.gitsearch.search.api

import com.example.gitsearch.constant.ApiUrl
import com.example.gitsearch.search.data.GitSearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitSearchManager {

    fun requestGitRepositories(query: String): Call<GitSearchResponse> {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ApiUrl.GitHubUrl.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: GitHubService = retrofit.create(GitHubService::class.java)

        return service.listRepos(query)
    }


}