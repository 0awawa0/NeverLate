package com.awawa.neverlate.ui.settings


import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.awawa.neverlate.R
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onStart() {
        btNotificationSettings.setOnClickListener {
            findNavController().navigate(R.id.nav_notifications)
        }
        super.onStart()
    }
}