package xyz.dnieln7.bubble

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import xyz.dnieln7.bubble.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var permissionsLauncher: ActivityResultLauncher<Array<String>>? = null
    private var overlayLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        permissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) {
            val allPermissionsGranted = it.values.all { true }

            if (allPermissionsGranted) {
                Toast.makeText(this, getString(R.string.thanks), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.permissions_are_required),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        overlayLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) {
            val canDrawOverlays = Settings.canDrawOverlays(this)

            if (canDrawOverlays) {
                attemptToTurnOn()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.permissions_are_required),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        binding.turnOn.setOnClickListener { attemptToTurnOn() }
        binding.turnOff.setOnClickListener {
            if (BatteryBubbleService.isActive) {
                stopService(Intent(this, BatteryBubbleService::class.java))
            }
        }

        requestPermissionsIfNeeded()
    }

    override fun onDestroy() {
        permissionsLauncher?.unregister()
        overlayLauncher?.unregister()

        super.onDestroy()
    }

    private fun requestPermissionsIfNeeded() {
        val notifications = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

        if (!notifications) {
            permissionsLauncher?.launch(buildPermissionsArray())
        }
    }

    private fun buildPermissionsArray(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            emptyArray()
        }
    }

    private fun attemptToTurnOn() {
        val canDrawOverlays = Settings.canDrawOverlays(this)

        if (canDrawOverlays) {
            if (!BatteryBubbleService.isActive) {
                startService(Intent(this, BatteryBubbleService::class.java))
            }
        } else {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName"),
            )

            overlayLauncher?.launch(intent)
        }
    }
}