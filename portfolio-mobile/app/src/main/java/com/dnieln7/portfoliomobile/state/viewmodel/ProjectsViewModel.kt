package com.dnieln7.portfoliomobile.state.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnieln7.portfoliomobile.data.repository.project.IProjectsRepository
import com.dnieln7.portfoliomobile.state.model.LoadingUIState
import kotlinx.coroutines.launch

class ProjectsViewModel(private val repository: IProjectsRepository) : ViewModel() {

    val projects = repository.getProjects()

    private val _uiState = MutableLiveData<LoadingUIState>()
    val uiState: LiveData<LoadingUIState> get() = _uiState

    var shouldReloadImages = false
        private set

    init {
        fetchProjects()
    }

    fun onImagesReloaded() {
        shouldReloadImages = false
    }

    fun fetchProjects(isRetry: Boolean = false) {
        _uiState.value = LoadingUIState.Loading
        shouldReloadImages = isRetry

        viewModelScope.launch {
            val response = repository.fetchProjects()

            if (response.success) {
                _uiState.value = LoadingUIState.Success
            } else {
                _uiState.value = LoadingUIState.Error(response.error)
            }
        }
    }
}