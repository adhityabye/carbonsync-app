package io.apaaja.carbonsync

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapsActivity : AppCompatActivity() {
    private val locationListener = LocationListener { location ->
        updateLocation(location)
    }
    private lateinit var map: MapView
    private var locationManager: LocationManager? = null
    private var previousLocation: Location? = null
    private var totalDistance = 0f
    private var transportMode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, getPreferences(MODE_PRIVATE))
        setContentView(R.layout.activity_maps)

        map = findViewById(R.id.map)
        map.setMultiTouchControls(true)
        map.controller.setZoom(15.0)

        // Initial marker at default location
        val startPoint = GeoPoint(51.5, -0.09)
        map.controller.setCenter(startPoint)

        transportMode = intent.getStringExtra("TRANSPORT_MODE")

        checkLocationPermissionAndSetup()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission granted, proceed with setting up location updates
            setupLocationUpdates()
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager?.removeUpdates(locationListener)
    }

    private fun checkLocationPermissionAndSetup() {
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is granted, set up location updates
            setupLocationUpdates()
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    private fun setupLocationUpdates() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let { location ->
                updateLocation(location)
            }

            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2000L,
                5f,
                locationListener
            )
        }
    }

    private fun updateLocation(location: Location) {
        // Update map marker
        val currentLocation = GeoPoint(location.latitude, location.longitude)
        val marker = Marker(map)
        marker.position = currentLocation
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        // Clear previous markers and add the new one
        map.overlays.clear()
        map.overlays.add(marker)

        // Move map to the user's current location
        map.controller.animateTo(currentLocation)  // Smoothly moves the map to the current location

        // Calculate distance traveled
        previousLocation?.let {
            val distance = it.distanceTo(location)
            totalDistance += distance
            Toast.makeText(this, "Total distance: $totalDistance meters", Toast.LENGTH_SHORT).show()
        }
        previousLocation = location
    }

    private fun returnToMainActivity() {
        val intent = Intent()
        intent.putExtra("TOTAL_DISTANCE", totalDistance)
        intent.putExtra("TRANSPORT_MODE", transportMode)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        returnToMainActivity()  // Go back to MainActivity with the result when the back button is pressed
    }
}