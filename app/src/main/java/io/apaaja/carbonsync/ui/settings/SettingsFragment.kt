package io.apaaja.carbonsync.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import io.apaaja.carbonsync.R

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val themePreference: ListPreference? = findPreference("theme")
        themePreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        themePreference?.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        if (preference.key == "theme") updateTheme(newValue as String)
        return true
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun updateTheme(selectedTheme: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        with(sharedPreferences.edit()) {
            putString("theme", selectedTheme)
            apply()
        }

        activity?.recreate()
    }
}