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

        fun getGithubRepositoryDatabase(context: Context): GithubRepositoryDatabase? {
            if(DATABASE_INSTANCE == null) {
                synchronized(GithubRepositoryDatabase::class) {

                    DATABASE_INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubRepositoryDatabase::class.java,
                        "github_repo_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return DATABASE_INSTANCE
        }

        fun destroyGithubRepositoryDatabase() {
            DATABASE_INSTANCE = null
        }

    }

}