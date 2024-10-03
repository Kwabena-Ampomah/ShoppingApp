package com.example.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.ui.theme.ShoppingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppTheme {
                MultiPaneShoppingApp()
            }
        }
    }
}

@Composable
fun MultiPaneShoppingApp() {
    // Sample products for the shopping list
    val products = listOf(
        Product("Product 1", "$100", "This jawn expensive."),
        Product("Product 2", "$150", "This is kinda more expensive by 50."),
        Product("Product 3", "$200", "Big spender product dont buy.")
    )
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val windowInfo = calculateCurrentWindowInfo()
    // copy from AdaptiveLayoutApp: dynamic layouts based on screen orientation
    if (windowInfo.isWideScreen) {

        Row(modifier = Modifier.fillMaxSize()) {
            ProductList(products, onProductSelected = { selectedProduct = it }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            ProductDetailsPane(selectedProduct, modifier = Modifier.weight(1f))
        }
    } else {
        if (selectedProduct == null) {
            ProductList(products, onProductSelected = { selectedProduct = it }, modifier = Modifier.fillMaxSize())
        } else {
            ProductDetailsPane(selectedProduct, modifier = Modifier.fillMaxSize())
        }
    }
}
@Composable
fun ProductList(products: List<Product>, onProductSelected: (Product) -> Unit, modifier: Modifier = Modifier) {
    // UI from DynamicSpacingFlowLayout: Handling lists and interactions
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Products", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        // Displaying the product list using LazyColumn
        LazyColumn {
            items(products) { product ->
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onProductSelected(product) }
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
@Composable
fun ProductDetailsPane(product: Product?, modifier: Modifier = Modifier) {
    // Detail pane behavior adapted from code3: Displaying task/product details
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (product != null) {

            Text(
                text = product.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Price: ${product.price}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description, fontSize = 16.sp)
        } else {
            // Placeholder message when no product is selected
            Text(
                text = "Select a product to view details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun calculateCurrentWindowInfo(): WindowInfo {
    // Adapted from DynamicSpacingFlowLayout: Dynamic layout calculation based on screen width
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val isWideScreen = screenWidth >= 600

    return WindowInfo(isWideScreen = isWideScreen)
}

data class WindowInfo(
    val isWideScreen: Boolean
)

data class Product(val name: String, val price: String, val description: String)

@Preview(showBackground = true)
@Composable
fun MultiPaneShoppingAppPreview() {
    ShoppingAppTheme {
        MultiPaneShoppingApp()
    }
}
