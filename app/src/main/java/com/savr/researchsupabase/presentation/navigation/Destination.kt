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