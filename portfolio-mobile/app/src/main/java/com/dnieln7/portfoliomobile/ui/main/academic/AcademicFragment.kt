package com.dnieln7.portfoliomobile.ui.main.academic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnieln7.portfoliomobile.PortfolioApplication
import com.dnieln7.portfoliomobile.R
import com.dnieln7.portfoliomobile.databinding.FragmentAcademicBinding
import com.dnieln7.portfoliomobile.state.ViewModelFactory
import com.dnieln7.portfoliomobile.state.model.ListUIState
import com.dnieln7.portfoliomobile.state.viewmodel.AcademicViewModel
import com.dnieln7.portfoliomobile.ui.main.portfolio.adapter.AcademicAdapter
import timber.log.Timber

class AcademicFragment : Fragment() {

    private var _binding: FragmentAcademicBinding? = null
    private val binding get() = _binding!!

    private val academicViewModel by viewModels<AcademicViewModel> {
        ViewModelFactory((requireActivity().application as PortfolioApplication).serviceLocator)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAcademicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        academicViewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                ListUIState.None -> Timber.i("uiState: None")
                ListUIState.Loading -> showProgress()
                is ListUIState.Success -> {
                    binding.items.adapter = AcademicAdapter(it.data)

                    showItems()
                }
                is ListUIState.Error -> showError(it.message)
            }
        }

        binding.education.setOnClickListener { findNavController().navigateUp() }
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
        binding.error.errorRetry.setOnClickListener { academicViewModel.getLogs() }
        binding.error.root.isVisible = true

        binding.items.isVisible = false
    }
}