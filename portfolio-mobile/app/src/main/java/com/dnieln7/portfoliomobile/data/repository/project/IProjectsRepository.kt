package com.dnieln7.portfoliomobile.data.repository.project

import com.dnieln7.portfoliomobile.domain.model.FetchResult
import com.dnieln7.portfoliomobile.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface IProjectsRepository {

    fun getProjects(): Flow<List<Project>>

    suspend fun fetchProjects(): FetchResult
}