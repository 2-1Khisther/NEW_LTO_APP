package com.example.new_lto_app


import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // TODO: once login is built, show the real logged-in user's name here
        findViewById<TextView>(R.id.tvUserName).text = "Khristher"

        findViewById<android.widget.ImageView>(R.id.btnEditProfile).setOnClickListener {
            // TODO: launch an Edit Profile screen
            toast("Edit profile")
        }

        findViewById<LinearLayout>(R.id.rowAccountSecurity).setOnClickListener {
            // TODO: launch an Account Security screen (change password, etc.)
            toast("Account Security")
        }

        // TODO: persist these toggle states (e.g. SharedPreferences) and apply
        // them for real — currently they just flip visually.
        findViewById<Switch>(R.id.switchNotification).setOnCheckedChangeListener { _, isChecked ->
            toast(if (isChecked) "Notifications on" else "Notifications off")
        }
        findViewById<Switch>(R.id.switchSound).setOnCheckedChangeListener { _, isChecked ->
            toast(if (isChecked) "Sound on" else "Sound off")
        }
        findViewById<Switch>(R.id.switchVibration).setOnCheckedChangeListener { _, isChecked ->
            toast(if (isChecked) "Vibration on" else "Vibration off")
        }
        findViewById<Switch>(R.id.switchDarkMode).setOnCheckedChangeListener { _, isChecked ->
            // TODO: actually apply dark mode (AppCompatDelegate.setDefaultNightMode)
            toast(if (isChecked) "Dark mode on" else "Dark mode off")
        }

        findViewById<TextView>(R.id.btnLogout).setOnClickListener {
            // TODO: clear the saved auth token / session here once login is built
            toast("Logged out")
        }

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)
        BottomNavHelper.setup(this, nav, BottomNavHelper.Screen.SETTINGS)
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
