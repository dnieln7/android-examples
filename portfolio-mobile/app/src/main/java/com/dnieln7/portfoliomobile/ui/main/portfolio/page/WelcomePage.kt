package com.dnieln7.portfoliomobile.ui.main.portfolio.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dnieln7.portfoliomobile.databinding.PageWelcomeBinding
import com.dnieln7.portfoliomobile.utils.openBrowser
import com.dnieln7.portfoliomobile.utils.openEMail

class WelcomePage : Fragment() {

    private var _binding: PageWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gmail.setOnClickListener { requireActivity().openEMail() }
        binding.github.setOnClickListener {
            requireActivity().openBrowser("https://github.com/dnieln7")
        }
        binding.linkedin.setOnClickListener {
            requireActivity().openBrowser("https://www.linkedin.com/in/daniel-alvarado-a9086320b")
        }
    }
}