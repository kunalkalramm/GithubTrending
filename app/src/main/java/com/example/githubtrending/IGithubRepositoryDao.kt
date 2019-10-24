package com.example.githubtrending

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IGithubRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoGithubRepositoryTable(roomGithubRepositoryModel: List<RoomGithubRepositoryModel>)

    @Update
    fun updateGithubRepositoryTable(roomGithubRepositoryModel: RoomGithubRepositoryModel)

    @Delete
    fun deleteRepositoryFromGithubRepository(roomGithubRepositoryModel: RoomGithubRepositoryModel)

    @Query("select * from Repository_Table")
    fun getAllRepositories(): LiveData<List<RoomGithubRepositoryModel>>

    @Query("delete from Repository_Table")
    fun deleteAllFromGithubRepository()

}