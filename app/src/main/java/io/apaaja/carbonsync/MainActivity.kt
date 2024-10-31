package io.apaaja.carbonsync

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import io.apaaja.carbonsync.databinding.ActivityMainBinding
import android.content.Intent
import androidx.navigation.NavController
import androidx.room.Room
import io.apaaja.carbonsync.database.AppDatabase
import io.apaaja.carbonsync.repository.CarbonActivitiesRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: CarbonActivitiesRepository
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        applyThemeFromPreferences()
        super.onCreate(savedInstanceState)

        database = AppDatabase.getDatabase(this)
        repository = CarbonActivitiesRepository(database.userDao())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        bottomNavItemChangeListener(navView, navController)
    }

    override fun onStart() {
        super.onStart()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun applyThemeFromPreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        when (sharedPreferences.getString("theme", "system")) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun bottomNavItemChangeListener(
        navView: BottomNavigationView,
        navController: NavController
    ) {
        navView.setOnItemSelectedListener { item ->
            navController.popBackStack(item.itemId, inclusive = true, saveState = false)
            navController.navigate(item.itemId)
            true
        }
    }

    fun openCommunityActivity(view: View) {
        val intent = Intent(this, CommunityActivity::class.java)
        startActivity(intent)
    }

    fun getCarbonActivitiesRepository(): CarbonActivitiesRepository {
        return repository
    }
}