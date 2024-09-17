package com.savr.researchsupabase.presentation.feature.productlist

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.savr.researchsupabase.R
import com.savr.researchsupabase.core.state.UIState
import com.savr.researchsupabase.presentation.navigation.AddProductDestination
import com.savr.researchsupabase.presentation.navigation.ProductListDestination
import com.savr.researchsupabase.presentation.navigation.SignInDestination

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductListScreen(
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel(),
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.getProducts()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val uiState by viewModel.uiState.collectAsState(initial = UIState.Idle)
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when(uiState){
            is UIState.Failure -> {
                Toast.makeText(context, "Failed to sign out ${(uiState as UIState.Failure).message}", Toast.LENGTH_SHORT).show()
            }
            is UIState.Idle -> { }
            is UIState.Success -> {
                Toast.makeText(context, "Sign out Successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(SignInDestination.route) {
                    popUpTo(ProductListDestination.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.getProducts()
            state.endRefresh()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.product_list_text_screen_title),
                        color = Color.Black,
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AddProductButton(onClick = { navController.navigate(AddProductDestination.route) })
        }
    ) {
        val productList = viewModel.productList.collectAsState(initial = listOf()).value
        Box(Modifier.nestedScroll(state.nestedScrollConnection)) {
            LazyColumn(
                modifier = Modifier.padding(top = 62.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(
                    items = productList,
                    key = { _, product -> product.id }) { _, item ->
                    ProductCard(item.name, item.price.toString(), item.imageUrl)
                }
            }
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = state,
            )
        }
    }
}

@Composable
private fun AddProductButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
        )
    }
}