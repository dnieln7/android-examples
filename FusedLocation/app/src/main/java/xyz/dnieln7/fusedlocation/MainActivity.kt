package xyz.dnieln7.fusedlocation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import xyz.dnieln7.fusedlocation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fusedLocationViewModel by viewModels<FusedLocationViewModel> {
        FusedLocationViewModel.Factory(LocationTracker().apply { onCreate(this@MainActivity) })
    }

    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private var permissionsLauncher: ActivityResultLauncher<Array<String>>? = null
    private var locationLauncher: ActivityResultLauncher<Intent>? = null

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

        locationLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                checkLocationsPermissionsAndState()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.location_is_required),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        fusedLocationViewModel.tracker.onCreate(this)

        lifecycleScope.launchWhenResumed {
            fusedLocationViewModel.tracker.lastKnownLocation.collect {
                if (it.location != null) {
                    val labels = getLocationLabels(it.location)

                    binding.lkLatitude.text = labels.first
                    binding.lkLongitude.text = labels.second
                    binding.lkAltitude.text = labels.third

                    binding.lastKnownLocation.isEnabled = true
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            fusedLocationViewModel.tracker.currentLocation.collect {
                if (it.location != null) {
                    val labels = getLocationLabels(it.location)

                    binding.clLatitude.text = labels.first
                    binding.clLongitude.text = labels.second
                    binding.clAltitude.text = labels.third

                    binding.currentLocation.isEnabled = true
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            fusedLocationViewModel.tracker.locationUpdates.collectLatest {
                if (it.location != null) {
                    val labels = getLocationLabels(it.location)

                    binding.luLatitude.text = labels.first
                    binding.luLongitude.text = labels.second
                    binding.luAltitude.text = labels.third
                }
            }
        }

        binding.lastKnownLocation.setOnClickListener {
            binding.lastKnownLocation.isEnabled = false
            fusedLocationViewModel.tracker.getLastKnownLocation()
        }
        binding.currentLocation.setOnClickListener {
            binding.currentLocation.isEnabled = false
            fusedLocationViewModel.tracker.calculateCurrentLocation()
        }
        binding.updatesSwitch.setOnCheckedChangeListener { _, isChecked ->
            fusedLocationViewModel.tracker.changeUpdatesSettings(isChecked)
        }

        checkLocationsPermissionsAndState()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationViewModel.tracker.onPause()
    }

    override fun onResume() {
        super.onResume()

        fusedLocationViewModel.tracker.onResume()
    }

    override fun onDestroy() {
        locationLauncher?.unregister()
        permissionsLauncher?.unregister()

        super.onDestroy()
    }

    private fun checkLocationsPermissionsAndState() {
        val location = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

        if (!location) {
            permissionsLauncher?.launch(buildPermissionsArray())
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationLauncher?.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun buildPermissionsArray(): Array<String> {
        return arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    }

    private fun getLocationLabels(location: Location): Triple<String, String, String> {
        val latitude = getString(R.string.latitude_placeholder, location.latitude.toString())
        val longitude = getString(R.string.longitude_placeholder, location.longitude.toString())
        val altitude = getString(R.string.altitude_placeholder, location.altitude.toString())

        return Triple(latitude, longitude, altitude)
    }
}