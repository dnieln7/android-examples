package com.dnieln7.portfoliomobile.state.viewmodel

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnieln7.portfoliomobile.data.preferences.PortfolioMobilePreferences
import com.dnieln7.portfoliomobile.domain.model.Project
import com.dnieln7.portfoliomobile.ui.main.MainSwipeOverlay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(private val preferences: PortfolioMobilePreferences) : ViewModel() {

    private val _currentPage = MutableStateFlow(PageData(-1, -1))
    val currentPage get() = _currentPage.asStateFlow()

    private val _navigator = MutableLiveData<NavigationDestination>()
    val navigator: LiveData<NavigationDestination> get() = _navigator

    private val _showOverlay = MutableLiveData<Boolean>()
    val showOverlay: LiveData<Boolean> get() = _showOverlay

    private var currentSwipePage: Int = 0

    init {
        _showOverlay.value = !preferences.isOverlayShown(MainSwipeOverlay.TAG)
    }

    fun setCurrentPage(page: PageData) {
        _currentPage.tryEmit(page)
    }

    fun setCurrentSwipePage(page: Int) {
        currentSwipePage = page
    }

    fun navigateBack() {
        _currentPage.tryEmit(PageData(currentSwipePage.minus(1), -1))
    }

    fun shouldExit(): Boolean {
        return currentSwipePage == 0
    }

    fun navigate(state: NavigationDestination) {
        _navigator.value = state
    }

    fun saveOverlayShown() {
        preferences.setOverlayShown(MainSwipeOverlay.TAG)
    }

    fun onNavigationCompleted() {
        _navigator.value = NavigationDestination.None
    }

    fun onOverlayShown() {
        _showOverlay.value = false
    }

    data class PageData(val page: Int, val timestamp: Long)

    sealed class NavigationDestination {
        object None : NavigationDestination()
        class ProjectDetail(val project: Project, val imageView: ImageView) :
            NavigationDestination()
        object Academic : NavigationDestination()
    }
}