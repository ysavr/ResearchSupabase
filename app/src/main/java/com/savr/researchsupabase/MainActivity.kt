package com.savr.researchsupabase

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.savr.researchsupabase.presentation.productlist.ProductListScreen
import com.savr.researchsupabase.ui.theme.ResearchSupabaseTheme
import com.savr.researchsupabase.presentation.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ProductViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResearchSupabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    ProductListScreen(viewModel)
                }
            }
        }
    }
}