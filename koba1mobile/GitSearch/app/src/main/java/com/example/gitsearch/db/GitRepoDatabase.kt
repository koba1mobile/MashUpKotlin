package com.example.gitsearch.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitsearch.data.GitRepo

@Database(entities = arrayOf(GitRepo::class), version = 3)
abstract class GitRepoDatabase : RoomDatabase() {
    abstract fun repoDao(): GitRepoDao
}