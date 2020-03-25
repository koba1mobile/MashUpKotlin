package com.example.gitsearch.search.data

import com.example.gitsearch.data.GitRepo

class GitSearchResponse (
    val total_count: Int,
    val items: List<GitRepo>
)