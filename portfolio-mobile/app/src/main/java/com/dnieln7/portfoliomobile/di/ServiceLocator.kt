package com.dnieln7.portfoliomobile.di

import android.content.Context
import com.dnieln7.portfoliomobile.data.database.AppDatabase
import com.dnieln7.portfoliomobile.data.preferences.PortfolioMobilePreferences
import com.dnieln7.portfoliomobile.data.repository.academic.AcademicRepository
import com.dnieln7.portfoliomobile.data.repository.academic.IAcademicRepository
import com.dnieln7.portfoliomobile.data.repository.project.IProjectsRepository
import com.dnieln7.portfoliomobile.data.repository.project.ProjectsRepository
import com.dnieln7.portfoliomobile.data.repository.skill.ISkillsRepository
import com.dnieln7.portfoliomobile.data.repository.skill.SkillsRepository
import com.dnieln7.portfoliomobile.data.repository.work.IWorkRepository
import com.dnieln7.portfoliomobile.data.repository.work.WorkRepository
import com.dnieln7.portfoliomobile.data.server.Api

class ServiceLocator(context: Context) {

    val preferences by lazy { PortfolioMobilePreferences().apply { onCreate(context) } }

    private val database by lazy { AppDatabase.getDatabase(context) }
    private val projectDao by lazy { database.projectDao() }

    private val api by lazy { Api("https://io.dnieln7.xyz/api/portfolio/") }
    private val projectsApi by lazy { api.portfolioApi }

    val skillsRepository: ISkillsRepository by lazy { SkillsRepository() }
    val workRepository: IWorkRepository by lazy { WorkRepository() }
    val academicRepository: IAcademicRepository by lazy { AcademicRepository() }
    val projectsRepository: IProjectsRepository by lazy {
        ProjectsRepository(projectDao, projectsApi)
    }
}