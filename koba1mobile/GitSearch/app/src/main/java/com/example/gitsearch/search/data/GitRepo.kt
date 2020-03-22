package com.example.gitsearch.search.data

import com.google.gson.annotations.SerializedName

class GitRepo (
    val full_name: String,

    val language: String?,

    @SerializedName("owner")
    val user: GitUser
)