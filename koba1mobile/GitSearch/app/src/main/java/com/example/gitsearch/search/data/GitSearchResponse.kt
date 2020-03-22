package com.example.gitsearch.search.data

import com.google.gson.annotations.SerializedName

class GitSearchResponse (
    val total_count: Int,
    val items: List<GitRepo>
)