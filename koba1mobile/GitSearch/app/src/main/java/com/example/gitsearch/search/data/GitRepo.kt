package com.example.gitsearch.search.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GitRepo (
    val full_name: String,

    val language: String?,

    @SerializedName("owner")
    val user: GitUser,

    val stargazers_count: Int,

    val description: String,

    val updated_at: String
): Serializable