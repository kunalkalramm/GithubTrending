package com.example.githubtrending.injection

import androidx.room.Room
import com.example.githubtrending.BaseApplication
import com.example.githubtrending.database.GithubRepositoryDatabase
import com.example.githubtrending.repository.GithubDatabaseSqlRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseModuleHelper.getDatabase(androidApplication() as BaseApplication) }
    single { GithubDatabaseSqlRepository(get()) }
}

object DatabaseModuleHelper {
    fun getDatabase(application: BaseApplication): GithubRepositoryDatabase {
        return Room.databaseBuilder(
            application,
            GithubRepositoryDatabase::class.java,
            "github_repo_database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
}