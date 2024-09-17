package com.savr.researchsupabase

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.savr.researchsupabase.presentation.navigation.SignInDestination
import com.savr.researchsupabase.presentation.navigation.navRegistration
import com.savr.researchsupabase.ui.theme.ResearchSupabaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResearchSupabaseTheme {
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = SignInDestination.route,
                        Modifier.padding(innerPadding)
                    ) {
                        navRegistration(navController)
                    }
                }
            }
        }
    }
}