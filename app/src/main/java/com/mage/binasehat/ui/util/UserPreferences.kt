package com.mage.binasehat.ui.util

import android.app.Activity
import android.content.Context
import java.util.Locale

object UserPreferences {
    private const val PREFS_NAME = "app_preferences"
    private const val KEY_LANGUAGE = "selected_language"
    private const val KEY_THEME = "theme_preference"

    fun saveLanguage(context: Context, language: String) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(KEY_LANGUAGE, language)
            apply()
        }
    }

    private fun getLanguage(context: Context): String {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(KEY_LANGUAGE, "in") ?: "in"
    }

    fun saveTheme(context: Context, isDarkMode: Boolean) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(KEY_THEME, isDarkMode)
            apply()
        }

        if (context is Activity) {
            context.recreate()
        }
    }

    fun getTheme(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(KEY_THEME, false)
    }

    fun updateLocale(context: Context) {
        val languageCode = getLanguage(context)
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

}