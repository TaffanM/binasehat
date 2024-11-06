package com.mage.binasehat

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.util.UserPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        if (sharedPref.getString("selected_language", null) == null) {
            UserPreferences.saveLanguage(this, "id") // Set default to Indonesian
        }

        // Update locale based on stored preference
        UserPreferences.updateLocale(this)

        setContent {
            val isDarkTheme = UserPreferences.getTheme(this)

            BinaSehatTheme(
                darkTheme = isDarkTheme,
            ) {
                SideEffect {
                    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
                        !isDarkTheme
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BinaSehatApp()
                }
            }
        }
    }

}