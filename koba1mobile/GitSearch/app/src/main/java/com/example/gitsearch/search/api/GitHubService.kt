package com.example.gitsearch.search.api

import com.example.gitsearch.search.data.GitSearchResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories")
    fun listRepos(@Query("q") query: String): Observable<GitSearchResponse>
}