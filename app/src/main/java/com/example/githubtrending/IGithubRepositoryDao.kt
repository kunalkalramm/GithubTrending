package com.example.githubtrending

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IGithubRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoGithubRepositoryTable(githubRepository: GithubRepository)

    @Update
    fun updateGithubRepositoryTable(githubRepository: GithubRepository)

    @Delete
    fun deleteRepositoryFromGithubRepository(githubRepository: GithubRepository)

    @Query("select * from Repository_Table")
    fun getAllRepositories(): LiveData<List<GithubRepository>>

    @Query("delete from Repository_Table")
    fun deleteAllFromGithubRepository()

}