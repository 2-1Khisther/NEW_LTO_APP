package com.example.new_lto_app

import android.app.Activity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

// Wires up the bottom nav the same way on every screen that has one.
// Call this once in onCreate(), right after findViewById<BottomNavigationView>(...).
//
// currentScreen tells it which tab to treat as "already here" so tapping it
// doesn't relaunch the same screen on top of itself.
object BottomNavHelper {

    // NONE = this screen (e.g. Non-Professional, Road Signs, Mock Exam) isn't
    // one of the 3 bottom-nav tabs itself, so no tab should be force-selected
    // and every tap should navigate normally.
    enum class Screen { HOME, PROFILE, SETTINGS, NONE }

    fun setup(activity: Activity, nav: BottomNavigationView, currentScreen: Screen) {
        // Show the correct tab as selected when the screen opens
        if (currentScreen != Screen.NONE) {
            nav.selectedItemId = when (currentScreen) {
                Screen.HOME -> R.id.nav_home
                Screen.PROFILE -> R.id.nav_profile
                Screen.SETTINGS -> R.id.nav_settings
                Screen.NONE -> R.id.nav_home // unreachable, satisfies the compiler
            }
        }

        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (currentScreen != Screen.HOME) {
                        // Clear everything above MainActivity so Home doesn't pile up
                        // a huge back stack every time it's tapped.
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.nav_profile -> {
                    if (currentScreen != Screen.PROFILE) {
                        activity.startActivity(Intent(activity, ProfileActivity::class.java))
                    }
                    true
                }
                R.id.nav_settings -> {
                    if (currentScreen != Screen.SETTINGS) {
                        activity.startActivity(Intent(activity, SettingsActivity::class.java))
                    }
                    true
                }
                else -> false
            }
        }
    }
}
