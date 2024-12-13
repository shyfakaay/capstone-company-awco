package com.example.capstone.navigate

import android.content.Context
import android.content.SharedPreferences

class NavigationPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Hapus metode yang tidak diperlukan
    fun clearNavigationState() {
        preferences.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "navigation_prefs"
    }
}