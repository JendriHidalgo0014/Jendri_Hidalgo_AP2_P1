package edu.ucne.jendri_hidalgo_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.jendri_hidalgo_ap2_p1.presentation.navigation.NavHost
import edu.ucne.jendri_hidalgo_ap2_p1.ui.theme.Jendri_Hidalgo_AP2_P1Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jendri_Hidalgo_AP2_P1Theme() {
                val navController = rememberNavController()
                NavHost(navController)
            }
        }
    }
}