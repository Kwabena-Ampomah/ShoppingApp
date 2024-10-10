package com.example.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

    val products = listOf(
        Product("Product 1", "$100", "This is expensive."),
        Product("Product 2", "$150", "More expensive by 50."),

    )


    val windowInfo = calculateCurrentWindowInfo()


    if (windowInfo.isWideScreen) {
        Row(modifier = Modifier.fillMaxSize()) {
            ProductDetailsList(products = products, modifier = Modifier.weight(1f).fillMaxHeight())
        }
    } else {
        ProductDetailsList(products = products, modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun ProductDetailsList(products: List<Product>, modifier: Modifier = Modifier) {

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(products) { product ->
            ProductDetailsPane(product = product, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProductDetailsPane(product: Product, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .padding(30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = product.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Price: ${product.price}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.description, fontSize = 16.sp)
    }
}

@Composable
fun calculateCurrentWindowInfo(): WindowInfo {

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
