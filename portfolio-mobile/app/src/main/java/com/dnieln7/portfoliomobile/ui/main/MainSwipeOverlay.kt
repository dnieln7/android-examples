package com.dnieln7.portfoliomobile.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dnieln7.portfoliomobile.databinding.OverlayMainSwipeBinding

class MainSwipeOverlay : AppCompatActivity() {

    private lateinit var binding: OverlayMainSwipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OverlayMainSwipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gotIt.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    companion object {
        const val TAG = "MainSwipeOverlay"
    }
}