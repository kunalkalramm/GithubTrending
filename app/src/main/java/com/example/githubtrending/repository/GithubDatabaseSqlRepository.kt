package com.example.githubtrending.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubtrending.GithubRepository
import com.example.githubtrending.GithubRepositoryDatabase
import com.example.githubtrending.IGithubRepositoryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GithubDatabaseSqlRepository(application: Application, scope: CoroutineScope) {

    private var allRepositories: LiveData<List<GithubRepository>>? = null
    private val githubRepositoryDao: IGithubRepositoryDao?

    init {
        val githubRepositoryDatabase =
            GithubRepositoryDatabase.getGithubRepositoryDatabase(application, scope)
        githubRepositoryDao = githubRepositoryDatabase?.getGithubRepositoryDao()
    }

    fun insertIntoDatabase(githubRepository: GithubRepository): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            githubRepositoryDao?.insertIntoGithubRepositoryTable(githubRepository)
        }
    }

    fun updateEntryInDatabase(githubRepository: GithubRepository):Job {
        return CoroutineScope(Dispatchers.IO).launch {
            githubRepositoryDao?.updateGithubRepositoryTable(githubRepository)
        }
    }

    fun deleteEntryFromDatabase(githubRepository: GithubRepository): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            githubRepositoryDao?.deleteRepositoryFromGithubRepository(githubRepository)
        }
    }

    fun deleteAllEntriesFromDatabase(): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            githubRepositoryDao?.deleteAllFromGithubRepository()
        }
    }

    fun getAllRepositories(githubRepository: GithubRepository): LiveData<List<GithubRepository>>? {
        return githubRepositoryDao?.getAllRepositories()
    }
}