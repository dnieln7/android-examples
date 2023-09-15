package com.dnieln7.portfoliomobile.ui.main.portfolio.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dnieln7.portfoliomobile.PortfolioApplication
import com.dnieln7.portfoliomobile.databinding.PageProjectsBinding
import com.dnieln7.portfoliomobile.state.ViewModelFactory
import com.dnieln7.portfoliomobile.state.model.LoadingUIState
import com.dnieln7.portfoliomobile.state.viewmodel.MainViewModel
import com.dnieln7.portfoliomobile.state.viewmodel.ProjectsViewModel
import com.dnieln7.portfoliomobile.ui.main.portfolio.adapter.ProjectAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProjectsPage : Fragment() {

    private var _binding: PageProjectsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProjectAdapter

    private val projectsViewModel by viewModels<ProjectsViewModel> {
        ViewModelFactory((requireActivity().application as PortfolioApplication).serviceLocator)
    }

    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory((requireActivity().application as PortfolioApplication).serviceLocator)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProjectAdapter { project, image ->
            mainViewModel.navigate(
                MainViewModel.NavigationDestination.ProjectDetail(project, image)
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                projectsViewModel.projects.collectLatest {
                    adapter.submitList(it)

                    if (projectsViewModel.shouldReloadImages) {
                        adapter.notifyItemRangeChanged(0, it.size)
                        projectsViewModel.onImagesReloaded()
                    }
                }
            }
        }

        projectsViewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                LoadingUIState.Loading -> showProgress()
                LoadingUIState.Success -> showItems()
                is LoadingUIState.Error -> showError(it.message)
            }
        }

        binding.items.setHasFixedSize(true)
        binding.items.adapter = adapter
    }

    private fun showProgress() {
        binding.progress.root.isVisible = true
        binding.localeData.root.isVisible = false
        binding.items.isVisible = false
    }

    private fun showItems() {
        binding.progress.root.isVisible = false
        binding.localeData.root.isVisible = false
        binding.items.isVisible = true
    }

    private fun showError(message: String?) {
        binding.localeData.retry.setOnClickListener {
            projectsViewModel.fetchProjects(true)
        }

        binding.progress.root.isVisible = false
        binding.localeData.root.isVisible = true
        binding.items.isVisible = true
    }
}