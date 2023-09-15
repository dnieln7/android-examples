package com.dnieln7.portfoliomobile.state.model

// TODO: 11/2/2022 Do i need none state?
sealed class LoadingUIState {
    object Loading : LoadingUIState()
    object Success : LoadingUIState()
    class Error(val message: String?) : LoadingUIState()
}
