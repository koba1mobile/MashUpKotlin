package com.example.gitsearch.db

import android.content.Context
import androidx.room.Room

class DatabaseManager {
    private var repoDatabase: GitRepoDatabase? = null

    private fun getRepoDatabaseInstance(context: Context): GitRepoDatabase {
        if(repoDatabase == null){
            repoDatabase = Room.databaseBuilder(context.applicationContext,
                GitRepoDatabase::class.java, "git_repo.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
        return repoDatabase!!
    }

    fun getGitRepoDao(context: Context): GitRepoDao = getRepoDatabaseInstance(context).repoDao()
}