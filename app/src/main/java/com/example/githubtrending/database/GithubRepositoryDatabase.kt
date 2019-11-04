package com.example.githubtrending.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubtrending.BaseApplication
import com.example.githubtrending.models.RoomModel

@Database(entities = [RoomModel::class], version = 1)
abstract class GithubRepositoryDatabase: RoomDatabase() {

    abstract fun getGithubRepositoryDao(): IGithubRepositoryDao

    companion object {
        private var DATABASE_INSTANCE: GithubRepositoryDatabase? = null

        fun getGithubRepositoryDatabase(): GithubRepositoryDatabase? {
            if(DATABASE_INSTANCE == null) {
                synchronized(GithubRepositoryDatabase::class) {

                    DATABASE_INSTANCE = Room.databaseBuilder(
                        BaseApplication.getInstance(),
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