package com.dnieln7.portfoliomobile.data.repository.project

import com.dnieln7.portfoliomobile.data.database.dao.ProjectDao
import com.dnieln7.portfoliomobile.data.server.project.PortfolioApi
import com.dnieln7.portfoliomobile.domain.model.FetchResult
import com.dnieln7.portfoliomobile.domain.model.Project
import kotlinx.coroutines.flow.Flow

class ProjectsRepository(
    private val projectDao: ProjectDao,
    private val portfolioApi: PortfolioApi
) : IProjectsRepository {

    override fun getProjects(): Flow<List<Project>> {
        return projectDao.get()
    }

    override suspend fun fetchProjects(): FetchResult {
        return try {
            val remoteProjects = portfolioApi.getProjects()

            projectDao.insert(remoteProjects)
            projectDao.deleteAllExcept(remoteProjects.map { it.id })

            FetchResult(true)
        } catch (e: Exception) {
            FetchResult(false, error = e.message)
        }
    }
}