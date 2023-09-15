package com.dnieln7.portfoliomobile.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dnieln7.portfoliomobile.di.ServiceLocator
import com.dnieln7.portfoliomobile.state.viewmodel.*

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val serviceLocator: ServiceLocator) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(serviceLocator.preferences) as T
        }

        if (modelClass.isAssignableFrom(SkillsViewModel::class.java)) {
            return SkillsViewModel(serviceLocator.skillsRepository) as T
        }

        if (modelClass.isAssignableFrom(WorkViewModel::class.java)) {
            return WorkViewModel(serviceLocator.workRepository) as T
        }

        if (modelClass.isAssignableFrom(AcademicViewModel::class.java)) {
            return AcademicViewModel(serviceLocator.academicRepository) as T
        }

        if (modelClass.isAssignableFrom(ProjectsViewModel::class.java)) {
            return ProjectsViewModel(serviceLocator.projectsRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}