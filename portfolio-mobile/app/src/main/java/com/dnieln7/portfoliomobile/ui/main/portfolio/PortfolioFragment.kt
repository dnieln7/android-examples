package com.dnieln7.portfoliomobile.ui.main.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.dnieln7.portfoliomobile.PortfolioApplication
import com.dnieln7.portfoliomobile.R
import com.dnieln7.portfoliomobile.databinding.FragmentMainBinding
import com.dnieln7.portfoliomobile.state.ViewModelFactory
import com.dnieln7.portfoliomobile.state.viewmodel.MainViewModel
import com.dnieln7.portfoliomobile.utils.navigate
import com.dnieln7.portfoliomobile.utils.pager.DepthPageTransformer
import com.dnieln7.portfoliomobile.utils.pager.PagerListener
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class PortfolioFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory((requireActivity().application as PortfolioApplication).serviceLocator)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.pager.setPageTransformer(DepthPageTransformer())
        binding.pager.adapter = PortfolioPagerAdapter(this)
        binding.pager.registerOnPageChangeCallback(PagerListener {
            mainViewModel.setCurrentPage(MainViewModel.PageData(it, -1))
            mainViewModel.setCurrentSwipePage(it)
            showCurrentPage(it)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetToolbar()

        lifecycleScope.launchWhenResumed {
            mainViewModel.currentPage.collect {
                if (it.page > -1 && binding.pager.currentItem != it.page) {
                    binding.pager.setCurrentItem(it.page, true)
                }
            }
        }

        mainViewModel.navigator.observe(viewLifecycleOwner) {
            when (it) {
                is MainViewModel.NavigationDestination.ProjectDetail -> {
                    PortfolioFragmentDirections.actionMainFragmentToProjectDetailFragment(it.project)
                        .navigate(binding.root)

                    mainViewModel.onNavigationCompleted()
                }
                MainViewModel.NavigationDestination.Academic -> {
                    PortfolioFragmentDirections.actionMainFragmentToAcademicFragment()
                        .navigate(binding.root)

                    mainViewModel.onNavigationCompleted()
                }
                MainViewModel.NavigationDestination.None -> Timber.i("navigator: None")
            }
        }

        binding.sideToolbar.home.setOnClickListener {
            extendMenuItem(binding.sideToolbar.home, 0)
        }

        binding.sideToolbar.skills.setOnClickListener {
            extendMenuItem(binding.sideToolbar.skills, 1)
        }

        binding.sideToolbar.highlights.setOnClickListener {
            extendMenuItem(binding.sideToolbar.highlights, 2)
        }

        binding.sideToolbar.projects.setOnClickListener {
            extendMenuItem(binding.sideToolbar.projects, 3)
        }
    }

    private fun extendMenuItem(item: ExtendedFloatingActionButton, page: Int? = null) {
        val listener = object : ExtendedFloatingActionButton.OnChangedCallback() {
            override fun onExtended(extendedFab: ExtendedFloatingActionButton?) {
                lifecycleScope.launch {
                    delay(500)
                    shrinkMenuItem(item = item, changeColor = false)
                }
            }
        }

        if (!item.isExtended) {
            item.extend(listener)
        }

        if (page != null) {
            mainViewModel.setCurrentPage(MainViewModel.PageData(page, System.currentTimeMillis()))
        }
    }

    private fun shrinkMenuItem(item: ExtendedFloatingActionButton, changeColor: Boolean = true) {
        if (item.isExtended) {
            item.shrink()
        }

        if (changeColor) {
            item.setIconTintResource(R.color.black_50)
        }
    }

    private fun showCurrentPage(page: Int) {
        when (page) {
            0 -> {
                shrinkMenuItem(binding.sideToolbar.skills)
                shrinkMenuItem(binding.sideToolbar.highlights)
                shrinkMenuItem(binding.sideToolbar.projects)

                extendMenuItem(binding.sideToolbar.home)
                binding.sideToolbar.home.setIconTintResource(R.color.dark21)
            }
            1 -> {
                shrinkMenuItem(binding.sideToolbar.home)
                shrinkMenuItem(binding.sideToolbar.highlights)
                shrinkMenuItem(binding.sideToolbar.projects)

                extendMenuItem(binding.sideToolbar.skills)
                binding.sideToolbar.skills.setIconTintResource(R.color.dark21)
            }
            2 -> {
                shrinkMenuItem(binding.sideToolbar.home)
                shrinkMenuItem(binding.sideToolbar.skills)
                shrinkMenuItem(binding.sideToolbar.projects)

                extendMenuItem(binding.sideToolbar.highlights)
                binding.sideToolbar.highlights.setIconTintResource(R.color.dark21)
            }
            3 -> {
                shrinkMenuItem(binding.sideToolbar.home)
                shrinkMenuItem(binding.sideToolbar.skills)
                shrinkMenuItem(binding.sideToolbar.highlights)

                extendMenuItem(binding.sideToolbar.projects)
                binding.sideToolbar.projects.setIconTintResource(R.color.dark21)
            }
        }
    }

    private fun resetToolbar() {
        binding.sideToolbar.home.shrink()
        binding.sideToolbar.skills.shrink()
        binding.sideToolbar.highlights.shrink()
        binding.sideToolbar.projects.shrink()
    }
}