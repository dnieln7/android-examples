package com.dnieln7.portfoliomobile.state.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnieln7.portfoliomobile.data.repository.academic.IAcademicRepository
import com.dnieln7.portfoliomobile.domain.model.AcademicLog
import com.dnieln7.portfoliomobile.state.model.ListUIState
import kotlinx.coroutines.launch

class AcademicViewModel(private val repository: IAcademicRepository) : ViewModel() {

    private val _uiState = MutableLiveData<ListUIState<AcademicLog>>()
    val uiState: LiveData<ListUIState<AcademicLog>> get() = _uiState

    init {
        getLogs()
    }

    fun getLogs() {
        _uiState.value = ListUIState.Loading

        viewModelScope.launch {
            val response = repository.getAcademicLogs()

            if (response.success) {
                _uiState.value = ListUIState.Success(response.data)
            } else {
                _uiState.value = ListUIState.Error(response.error)
            }
        }
    }

    fun resetUiState() {
        _uiState.value = ListUIState.None
    }
}