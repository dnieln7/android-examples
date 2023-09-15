package com.dnieln7.portfoliomobile.state.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnieln7.portfoliomobile.data.repository.skill.ISkillsRepository
import com.dnieln7.portfoliomobile.domain.model.Skill
import com.dnieln7.portfoliomobile.state.model.ListUIState
import kotlinx.coroutines.launch

class SkillsViewModel(private val repository: ISkillsRepository) : ViewModel() {

    private val _uiState = MutableLiveData<ListUIState<Skill>>()
    val uiState: LiveData<ListUIState<Skill>> get() = _uiState

    init {
        getSkills()
    }

    fun getSkills() {
        _uiState.value = ListUIState.Loading

        viewModelScope.launch {
            val response = repository.getSkills()

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