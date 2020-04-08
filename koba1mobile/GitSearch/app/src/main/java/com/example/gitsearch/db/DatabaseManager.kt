package com.example.gitsearch.db

import android.content.Context
import androidx.room.Room

class DatabaseManager {
    private fun getRepoDatabaseInstance(context: Context): GitRepoDatabase {
        return Room.databaseBuilder(context.applicationContext,
            GitRepoDatabase::class.java, "git_repo.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getGitRepoDao(context: Context): GitRepoDao = getRepoDatabaseInstance(context).repoDao()
}