package com.savr.researchsupabase.presentation.navigation

interface Destination {
    val route: String
    val title: String
}

object ProductListDestination : Destination {
    override val route = "product_list"
    override val title = "Product List"
}

object AddProductDestination : Destination {
    override val route = "add_product"
    override val title = "Add Product"
}

object SignUpDestination : Destination {
    override val route = "sign_up"
    override val title = "Sign Up"
}

object SignInDestination : Destination {
    override val route = "sign_in"
    override val title = "Sign In"
}