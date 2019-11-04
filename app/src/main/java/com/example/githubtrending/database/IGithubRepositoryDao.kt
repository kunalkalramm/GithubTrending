package com.example.githubtrending.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubtrending.models.RoomModel

@Dao
interface IGithubRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoGithubRepositoryTable(roomModel: List<RoomModel>)

    @Update
    fun updateGithubRepositoryTable(roomModel: RoomModel)

    @Delete
    fun deleteRepositoryFromGithubRepository(roomModel: RoomModel)

    @Query("select * from Repository_Table")
    fun getAllRepositories(): LiveData<List<RoomModel>>

    @Query("delete from Repository_Table")
    fun deleteAllFromGithubRepository()

}