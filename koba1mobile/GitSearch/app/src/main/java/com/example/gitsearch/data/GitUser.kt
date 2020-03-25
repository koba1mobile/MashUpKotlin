package com.example.gitsearch.data

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class GitUser (
    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    val thumbnail_url: String?
)