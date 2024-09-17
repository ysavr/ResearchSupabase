package com.savr.researchsupabase.presentation.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.savr.researchsupabase.presentation.feature.addproduct.AddProductScreen
import com.savr.researchsupabase.presentation.feature.productlist.ProductListScreen
import com.savr.researchsupabase.presentation.feature.signin.SignInScreen
import com.savr.researchsupabase.presentation.feature.signup.SignUpScreen

@SuppressLint("NewApi")
fun NavGraphBuilder.navRegistration(navController: NavController) {
    composable(ProductListDestination.route) {
        ProductListScreen(navController = navController)
    }
    composable(AddProductDestination.route) {
        AddProductScreen(navController = navController)
    }
    composable(SignUpDestination.route) {
        SignUpScreen(navController = navController)
    }
    composable(SignInDestination.route) {
        SignInScreen(navController = navController)
    }
}