package xyz.dnieln7.media.screenshot

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import xyz.dnieln7.media.R
import xyz.dnieln7.media.common.launchWithLifecycle
import xyz.dnieln7.media.common.toastShort
import xyz.dnieln7.media.databinding.ActivityScreenshotBinding
import xyz.dnieln7.media.databinding.DialogScreenshotBinding

class ScreenshotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenshotBinding

    private val screenshotHelper = ScreenshotHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenshotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launchWithLifecycle {
            screenshotHelper.screenshotStatus.collect {
                when (it) {
                    ScreenshotStatus.Ready -> println("screenshotHelper.screenshotStatus.Ready")
                    ScreenshotStatus.Failed -> toastShort(R.string.screenshot_error)
                    is ScreenshotStatus.Taken -> showScreenshotDialog(it.uri)
                }
            }
        }

        binding.captureApple.setOnClickListener {
            screenshotHelper.takeScreenShot(binding.apple, true)
        }

        binding.captureFruits.setOnClickListener {
            screenshotHelper.takeScreenShot(binding.fruits, false)
        }

        binding.captureEverything.setOnClickListener {
//            binding.captureApple.isVisible = false
//            binding.captureFruits.isVisible = false
//            binding.captureEverything.isVisible = false

            screenshotHelper.takeScreenShot(binding.root, false)

//            binding.captureApple.isVisible = true
//            binding.captureFruits.isVisible = true
//            binding.captureEverything.isVisible = true
        }
    }

    private fun showScreenshotDialog(uri: Uri) {
        val dialogBinding = DialogScreenshotBinding.inflate(LayoutInflater.from(this))
        val dialogBuilder = MaterialAlertDialogBuilder(this)

        dialogBuilder.setView(dialogBinding.root)

        val dialog = dialogBuilder.create()

        dialogBinding.image.load(uri) { crossfade(true) }
        dialogBinding.close.setOnClickListener {
            screenshotHelper.resetStatus()
            dialog.dismiss()
        }

        dialog.show()
    }
}