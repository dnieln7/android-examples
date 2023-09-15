package com.dnieln7.portfoliomobile.state.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnieln7.portfoliomobile.data.repository.work.IWorkRepository
import com.dnieln7.portfoliomobile.domain.model.WorkLog
import com.dnieln7.portfoliomobile.state.model.ListUIState
import kotlinx.coroutines.launch

class WorkViewModel(private val repository: IWorkRepository) : ViewModel() {

    private val _uiState = MutableLiveData<ListUIState<WorkLog>>()
    val uiState: LiveData<ListUIState<WorkLog>> get() = _uiState

    init {
        getLogs()
    }

    fun getLogs() {
        _uiState.value = ListUIState.Loading

        viewModelScope.launch {
            val response = repository.getWorkLogs()

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