package com.mage.binasehat.ui.screen.food

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mage.binasehat.R
import com.mage.binasehat.data.local.fake.FakeData
import com.mage.binasehat.data.model.Food
import com.mage.binasehat.ui.model.CartItem
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.theme.Typography
import kotlinx.coroutines.delay


@Composable
fun FoodListScreen(
    navController: NavController,
    foodViewModel: FoodViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    val cartItems by foodViewModel.cartItems.collectAsState()
    val showBottomSheet = remember { mutableStateOf(false) }
    val showFloatingButton = remember { mutableStateOf(false) }

    LaunchedEffect(cartItems) {
        if (cartItems.isNotEmpty()) {
            showFloatingButton.value = true
        }
    }

    Log.d("FoodListScreen", "Added Items: $cartItems")
    Log.d("FoodListScreen", "Added Items Size: ${cartItems.size}")
    Log.d("FoodListScreen", "Show Floating Button: ${showFloatingButton.value}")

    // Filter the food data based on the search query
    val filteredFoodData = FakeData.fakeFoodData.filter { food ->
        food.name.contains(searchQuery, ignoreCase = true)
    }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
                isSearchActive = false
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 32.dp)
        ) {
            Row {
                BackButton(
                    text = stringResource(R.string.scan_kamera),
                    onClick = { navController.popBackStack() },
                    icon = painterResource(R.drawable.round_arrow_back_ios_24),
                )
                Spacer(modifier = Modifier.weight(1f))

            }

            
            Spacer(modifier = Modifier.height(16.dp))

            CustomSearchBar(
                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery
                },
                modifier = Modifier
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                items(filteredFoodData) {food ->
                    FoodItem(food = food) {
                        navController.navigate("detailFoodScreen/${food.foodId}")
                    }
                    HorizontalDivider(
                        color = colorResource(R.color.gray).copy(alpha = 0.2f),
                    )
                }
            }
        }
        if (showFloatingButton.value) {
            Button(
                onClick = {
                    navController.navigate("cart") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.green_primary),
                    contentColor = colorResource(R.color.white_bg)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(stringResource(R.string.item_ditambahkan), style = Typography.bodyMedium)
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(stringResource(R.string.total_item) + " : " + "(${cartItems.size})", style = Typography.bodySmall, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.rounded_chevron_forward_24),
                    contentDescription = "Add"
                )

            }
        }
//        if (showBottomSheet.value) {
//            BottomSheetDialog(
//                cartItems = cartItems,
//                onDismissRequest = {
//                    showBottomSheet.value = false
//               },
//                onItemRemove = { food ->
//                    foodViewModel.removeItem(food)
//                    if (cartItems.isEmpty()) {
//                        showFloatingButton.value = false
//                    }
//                },
//                navController = navController
//            )
//        }

    }


}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BottomSheetDialog(
//    cartItems: List<CartItem>,
//    onDismissRequest: () -> Unit,
//    onItemRemove: (Food) -> Unit,
//    navController: NavController
//) {
//    val sheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = true
//    )
//
//
//    ModalBottomSheet(
//        onDismissRequest = onDismissRequest,
//        sheetState = sheetState,
//        containerColor = MaterialTheme.colorScheme.background,
//        contentWindowInsets = {
//            BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom)
//        },
//    ) {
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(stringResource(R.string.item_ditambahkan), style = Typography.titleMedium)
//                    Spacer(modifier = Modifier.weight(1f))
//                    IconButton(
//                        onClick = onDismissRequest,
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.round_clear_24),
//                            contentDescription = "Close"
//                        )
//                    }
//                }
//                Text(stringResource(R.string.total_item) + " : " + "(${cartItems.size})", style = Typography.bodySmall,  fontWeight = FontWeight.Bold)
//
//                Spacer(modifier = Modifier.padding(vertical = 8.dp))
//
//                LazyColumn(
//                    modifier = Modifier
//                        .weight(1f),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    items(cartItems, key = { it.food.foodId }) { food ->
//                        SwipeToDeleteContainer(
//                            item = food,
//                            onDelete = { onItemRemove(it.food) }
//                        ) { item ->
//                            Card {
//                                Row(
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .background(MaterialTheme.colorScheme.background)
//                                        .padding(vertical = 16.dp)
//                                ) {
//                                    Card(
//                                        modifier = Modifier
//                                            .width(100.dp)
//                                            .height(100.dp),
//                                        shape = RoundedCornerShape(20.dp),
//                                    ) {
//                                        AsyncImage(
//                                            model = item.food.photo,
//                                            contentDescription = item.food.name,
//                                        )
//                                    }
//
//                                    Column(
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .padding(start = 16.dp),
//                                        verticalArrangement = Arrangement.Center
//                                    ) {
//                                        Text(
//                                            style = Typography.titleMedium,
//                                            fontWeight = FontWeight.Bold,
//                                            text = item.food.name,
//                                        )
//                                        Text(
//                                            style = Typography.bodySmall,
//                                            text = stringResource(R.string.qty) + " : " + item.quantity.toString(),
//                                            modifier = Modifier.padding(top = 4.dp)
//                                        )
//                                    }
//                                }
//                            }
//
//
//                        }
//                    }
//                }
//
//                Button(
//                    onClick = {
//                        onDismissRequest()
//                        navController.navigate("cart")
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = colorResource(R.color.green_primary),
//                        contentColor = colorResource(R.color.white_bg)
//                    ),
//                    shape = RoundedCornerShape(10.dp)
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(stringResource(R.string.selanjutnya), style = Typography.bodyMedium)
//                        Spacer(modifier = Modifier.padding(4.dp))
//                        Icon(
//                            painter = painterResource(R.drawable.round_arrow_forward_24),
//                            contentDescription = null
//                        )
//                    }
//                }
//            }
//        }
//    }
//}


@Composable
fun FoodItem(
    food: Food,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = colorResource(R.color.green_primary),
            disabledContentColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    AsyncImage(
                        model = food.photo,
                        contentDescription = food.name,
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        text = food.name,

                    )
                    Text(
                        style = Typography.bodySmall,
                        text = food.desc,
                        maxLines = 2,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }

            }

        }
    }
}


@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp, max = 52.dp),
        placeholder = {
            Text(
                stringResource(R.string.cari_makanan),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                style = Typography.bodyMedium

            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.rounded_search_24),
                contentDescription = null
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onQueryChange("") }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.round_clear_24),
                        contentDescription = null
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        textStyle = Typography.bodyMedium

    )
}

@Preview
@Composable
fun FoodItemPreview() {
    BinaSehatTheme(darkTheme = false) {
        FoodItem(food = FakeData.fakeFoodData[0], onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun FoodListScreenPreview() {
    BinaSehatTheme(darkTheme = false) {
        FoodListScreen(navController = NavController(LocalContext.current))
    }
}

