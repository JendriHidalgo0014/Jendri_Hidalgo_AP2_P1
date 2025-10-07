package edu.ucne.jendri_hidalgo_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.jendri_hidalgo_ap2_p1.presentation.navigation.Jendri_Hidalgo_AP2_P1NavHost
import edu.ucne.jendri_hidalgo_ap2_p1.ui.theme.Jendri_Hidalgo_AP2_P1Theme

@AndroidEntryPoint // ← ESTA ANOTACIÓN ES ESENCIAL
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jendri_Hidalgo_AP2_P1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Jendri_Hidalgo_AP2_P1NavHost(navController = navController)
                }
            }
        }
    }
}