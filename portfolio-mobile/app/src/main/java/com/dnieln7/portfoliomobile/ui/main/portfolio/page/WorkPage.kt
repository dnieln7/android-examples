package com.dnieln7.portfoliomobile.ui.main.portfolio.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dnieln7.portfoliomobile.PortfolioApplication
import com.dnieln7.portfoliomobile.R
import com.dnieln7.portfoliomobile.databinding.PageWorkBinding
import com.dnieln7.portfoliomobile.state.ViewModelFactory
import com.dnieln7.portfoliomobile.state.model.ListUIState
import com.dnieln7.portfoliomobile.state.viewmodel.MainViewModel
import com.dnieln7.portfoliomobile.state.viewmodel.WorkViewModel
import com.dnieln7.portfoliomobile.ui.main.portfolio.adapter.WorkAdapter
import timber.log.Timber

class WorkPage : Fragment() {

    private var _binding: PageWorkBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory((requireActivity().application as PortfolioApplication).serviceLocator)
    }

    private val workViewModel by viewModels<WorkViewModel> {
        ViewModelFactory((requireActivity().application as PortfolioApplication).serviceLocator)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workViewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                ListUIState.None -> Timber.i("uiState: None")
                ListUIState.Loading -> showProgress()
                is ListUIState.Success -> {
                    binding.items.adapter = WorkAdapter(it.data)

                    showItems()
                }
                is ListUIState.Error -> showError(it.message)
            }
        }

        binding.education.setOnClickListener {
            mainViewModel.navigate(MainViewModel.NavigationDestination.Academic)
        }
    }

    private fun showProgress() {
        binding.progress.root.isVisible = true
        binding.error.root.isVisible = false
        binding.items.isVisible = false
    }

    private fun showItems() {
        binding.progress.root.isVisible = false
        binding.error.root.isVisible = false
        binding.items.isVisible = true
    }

    private fun showError(message: String?) {
        binding.progress.root.isVisible = false

        binding.error.errorMessage.text = message ?: getText(R.string.error)
        binding.error.errorRetry.setOnClickListener { workViewModel.getLogs() }
        binding.error.root.isVisible = true

        binding.items.isVisible = false
    }
}