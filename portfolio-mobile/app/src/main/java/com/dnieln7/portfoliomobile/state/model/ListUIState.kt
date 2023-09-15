package com.dnieln7.portfoliomobile.state.model

sealed class ListUIState<out T> {
    object None : ListUIState<Nothing>()
    object Loading : ListUIState<Nothing>()
    class Error(val message: String?) : ListUIState<Nothing>()
    class Success<T>(val data: List<T>) : ListUIState<T>()
}