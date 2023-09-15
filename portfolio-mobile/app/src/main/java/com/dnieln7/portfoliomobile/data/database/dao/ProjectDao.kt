package com.dnieln7.portfoliomobile.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dnieln7.portfoliomobile.domain.model.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(projects: List<Project>)

    @Query("SELECT * FROM tb_projects ORDER BY importance DESC")
    fun get(): Flow<List<Project>>

    @Query("DELETE FROM tb_projects WHERE id NOT IN (:ids)")
    suspend fun deleteAllExcept(ids: List<Int>)
}