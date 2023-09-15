package com.dnieln7.portfoliomobile.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.dnieln7.portfoliomobile.PortfolioApplication
import com.dnieln7.portfoliomobile.R
import com.dnieln7.portfoliomobile.databinding.ActivityMainBinding
import com.dnieln7.portfoliomobile.service.UpdateService
import com.dnieln7.portfoliomobile.state.ViewModelFactory
import com.dnieln7.portfoliomobile.state.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory((application as PortfolioApplication).serviceLocator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UpdateService.verify(this)

        val host = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = host.navController

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                mainViewModel.saveOverlayShown()
            }
        }

        mainViewModel.showOverlay.observe(this) {
            if (it) {
                launcher.launch(Intent(this, MainSwipeOverlay::class.java))
                mainViewModel.onOverlayShown()
            }
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.label == "fragment_main") {
            if (mainViewModel.shouldExit()) {
                super.onBackPressed()
            } else {
                mainViewModel.navigateBack()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host).navigateUp()
    }
}