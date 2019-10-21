package com.example.githubtrending

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IGithubRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoGithubRepositoryTable(githubRepository: GithubRepository)

    @Update
    suspend fun updateGithubRepositoryTable(githubRepository: GithubRepository)

    @Delete
    suspend fun deleteRepositoryFromGithubRepository(githubRepository: GithubRepository)

    @Query("select * from Repository_Table")
    fun getAllRepositories(): LiveData<List<GithubRepository>>

    @Query("delete from Repository_Table")
    suspend fun deleteAllFromGithubRepository()

}