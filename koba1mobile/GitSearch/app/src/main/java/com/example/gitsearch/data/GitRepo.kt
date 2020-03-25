package com.example.gitsearch.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitsearch.common.list.ItemData
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "GitRepo")
data class GitRepo (
    @PrimaryKey
    @ColumnInfo(name = "full_name")
    val full_name: String,

    @ColumnInfo(name = "language")
    val language: String?,

    @Embedded
    @SerializedName("owner")
    val user: GitUser,

    val stargazers_count: Int,

    val description: String,

    val updated_at: String
): Serializable, ItemData