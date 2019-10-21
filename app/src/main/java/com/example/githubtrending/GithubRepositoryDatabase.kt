package com.example.githubtrending

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [GithubRepository::class], version = 1)
abstract class GithubRepositoryDatabase: RoomDatabase() {

    abstract fun getGithubRepositoryDao(): IGithubRepositoryDao

    companion object {
        private var DATABASE_INSTANCE: GithubRepositoryDatabase? = null

        fun getGithubRepositoryDatabase(context: Context, scope: CoroutineScope): GithubRepositoryDatabase? {
            if(DATABASE_INSTANCE == null) {
                synchronized(GithubRepositoryDatabase::class) {

                    DATABASE_INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubRepositoryDatabase::class.java,
                        "github_repo_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(GithubRepositoryDatabaseCallback(scope))
                        .build()
                }
            }

            return DATABASE_INSTANCE
        }

        fun destroyGithubRepositoryDatabase() {
            DATABASE_INSTANCE = null
        }

    }

    private class GithubRepositoryDatabaseCallback(val scope: CoroutineScope): RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            DATABASE_INSTANCE.let {
                scope.launch { populateDatabase(it?.getGithubRepositoryDao()) }
            }
        }

        suspend fun populateDatabase(dao: IGithubRepositoryDao?) {
            val sampleGithubRepo = GithubRepository(
                repoId = 0,
                author = "Kunal Kalra",
                repoName = "Trending Repos",
                avatarURL = "http://github.com/kunalkalra97/avatar",
                repoURL = "http://github.com/kunalkalra97/trendingRepo",
                description = "This is a sample repository entry made by Kunal Kalra",
                stars = 10,
                forks = 10
            )

            dao?.insertIntoGithubRepositoryTable(sampleGithubRepo)
        }
    }

}