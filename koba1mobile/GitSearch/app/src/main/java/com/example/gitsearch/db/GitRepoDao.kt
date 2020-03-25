package com.example.gitsearch.db

import androidx.room.*
import com.example.gitsearch.data.GitRepo
import com.example.gitsearch.data.GitUser

@Dao
interface GitRepoDao {
    @Query("SELECT * FROM GitRepo")
    fun getAll(): List<GitRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repo: GitRepo)

    @Delete
    fun delete(repo: GitRepo)
}