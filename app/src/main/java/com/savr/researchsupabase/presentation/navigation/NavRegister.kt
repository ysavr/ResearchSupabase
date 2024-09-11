package com.savr.researchsupabase.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.savr.researchsupabase.presentation.feature.addproduct.AddProductScreen
import com.savr.researchsupabase.presentation.feature.productlist.ProductListScreen

fun NavGraphBuilder.navRegistration(navController: NavController) {
    composable(ProductListDestination.route) {
        ProductListScreen(navController)
    }
    composable(AddProductDestination.route) {
        AddProductScreen(navController)
    }
}