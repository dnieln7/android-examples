package com.dnieln7.portfoliomobile.ui.main.projectdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dnieln7.portfoliomobile.R
import com.dnieln7.portfoliomobile.databinding.FragmentProjectDetailBinding
import com.dnieln7.portfoliomobile.databinding.UiAccessLinkBinding
import com.dnieln7.portfoliomobile.databinding.UiIconTextBinding
import com.dnieln7.portfoliomobile.domain.model.Project
import com.dnieln7.portfoliomobile.utils.openBrowser
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class ProjectDetailFragment : Fragment() {
    private var _binding: FragmentProjectDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ProjectDetailFragmentArgs>()

    private var carouselJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectDetailBinding.inflate(inflater, container, false)

        binding.pager.adapter = ImagePagerAdapter(args.project.images)

        carouselJob = lifecycleScope.launch {
            while (isActive) {
                delay(2000)

                val next = binding.pager.currentItem.inc().takeIf { new ->
                    new < args.project.images.size
                }

                binding.pager.setCurrentItem(next ?: 0, true)
            }
        }

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)

                binding.appBarContainer?.updateLayoutParams<CoordinatorLayout.LayoutParams> {
                    height = (binding.root.height - (binding.root.height / 4F)).roundToInt()
                }
            }
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        carouselJob?.cancel()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = args.project.name
        binding.duration.text = args.project.duration
        binding.description.text = args.project.description

        val ownership = if (args.project.ownership == "N/A") {
            getString(
                R.string.ownership_description,
                args.project.name,
                args.project.name,
                args.project.year
            )
        } else {
            args.project.ownership
        }

        binding.ownership.text = ownership

        initActionLinks(args.project)

        args.project.technologies.forEach {
            val technology = UiIconTextBinding.inflate(
                LayoutInflater.from(requireContext()),
                binding.technologies,
                false
            ).apply {
                root.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_arrow_right,
                    0,
                    0,
                    0
                )
            }

            technology.root.text = it
            binding.technologies.addView(technology.root)
        }

        args.project.features.forEach {
            val feature = UiIconTextBinding.inflate(
                LayoutInflater.from(requireContext()),
                binding.features,
                false
            ).apply {
                root.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_check,
                    0,
                    0,
                    0
                )
            }

            feature.root.text = it
            binding.features.addView(feature.root)
        }
    }

    private fun initActionLinks(project: Project) {
        if (project.androidUrl != "N/A") {
            binding.accessLinks.addView(
                createAccessLink(
                    R.drawable.ic_app_android,
                    R.string.android_app,
                    project.androidUrl
                )
            )
        }

        if (project.androidGit != "N/A") {
            binding.accessLinks.addView(
                createAccessLink(
                    R.drawable.ic_app_android_source,
                    R.string.android_app_source_code,
                    project.androidGit
                )
            )
        }

        if (project.webUrl != "N/A") {
            binding.accessLinks.addView(
                createAccessLink(R.drawable.ic_web, R.string.web_app, project.webUrl)
            )
        }

        if (project.webGit != "N/A") {
            binding.accessLinks.addView(
                createAccessLink(
                    R.drawable.ic_app_source,
                    R.string.web_app_source_code,
                    project.webGit
                )
            )
        }

        if (project.programUrl != "N/A") {
            binding.accessLinks.addView(
                createAccessLink(R.drawable.ic_app, R.string.app, project.programUrl)
            )
        }

        if (project.programGit != "N/A") {
            binding.accessLinks.addView(
                createAccessLink(
                    R.drawable.ic_app_source,
                    R.string.app_source_code,
                    project.programGit
                )
            )
        }
    }

    private fun createAccessLink(iconRes: Int, labelRes: Int, link: String): MaterialButton {
        return UiAccessLinkBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.accessLinks,
            false
        ).apply {
            root.setText(labelRes)
            root.setIconResource(iconRes)
            root.setOnClickListener { requireContext().openBrowser(link) }
        }.root
    }
}