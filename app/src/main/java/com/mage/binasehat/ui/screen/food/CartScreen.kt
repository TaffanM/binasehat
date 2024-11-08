package com.mage.binasehat.ui.screen.food

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mage.binasehat.R
import com.mage.binasehat.data.local.fake.FakeData
import com.mage.binasehat.ui.model.CartItem
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.CustomFillButton
import com.mage.binasehat.ui.screen.components.CustomOutlinedButton
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.theme.Typography
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartScreen(
    navController: NavController,
    foodViewModel: FoodViewModel = viewModel()
) {
    val cartItems = foodViewModel.cartItems.collectAsState()
    val totalCalories = cartItems.value.sumOf { it.food.calories * it.quantity }

    // calculate totals of nutritional values
    val totalProtein = cartItems.value.map { it.food.protein * it.quantity.toFloat() }.sum()
    val totalCarb = cartItems.value.map { it.food.carb * it.quantity.toFloat() }.sum()
    val totalSugar = cartItems.value.map { it.food.sugar * it.quantity.toFloat() }.sum()
    val totalFat = cartItems.value.map { it.food.fat * it.quantity.toFloat() }.sum()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Cart content and list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            stickyHeader {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    BackButton(
                        text = stringResource(R.string.list_makanan),
                        onClick = { navController.popBackStack() },
                        icon = painterResource(R.drawable.round_arrow_back_ios_24),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.list_makanan),
                        style = Typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

            }

            // Content section
            if (cartItems.value.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.list_makanan_kosong),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            } else {
                items(cartItems.value, key = { it.food.foodId }) { cartItem ->
                    SwipeToDeleteContainer(
                        item = cartItem,
                        onDelete = { foodViewModel.removeItem(cartItem.food) }
                    ) { item ->
                        CartItemLayout(
                            cartItem = item,
                            onIncreaseQuantity = { foodViewModel.increaseQuantity(item.food) },
                            onDecreaseQuantity = { foodViewModel.decreaseQuantity(item.food) },
                            onDelete = { foodViewModel.removeItem(item.food) }
                        )
                    }
                    HorizontalDivider()
                }
            }

            // Footer section
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.ada_lagi_yang_mau_ditambah),
                            style = Typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(0.5f)
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = stringResource(R.string.tambahkan_hidangan_lain),
                            style = Typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.widthIn(max = 150.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    CustomOutlinedButton(
                        text = stringResource(R.string.tambah),
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.width(150.dp)
                    )
                }
                HorizontalDivider()
                Text(
                    text = stringResource(R.string.detail_nutrisi),
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Column(
                    modifier = Modifier
                        .border(border = BorderStroke(1.dp, colorResource(R.color.gray_300)), shape = RoundedCornerShape(10))
                        .padding(8.dp)
                ) {
                    cartItems.value.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.food.name,
                                    fontWeight = FontWeight.Bold,
                                    style = Typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = "${item.food.calories * item.quantity} cal", // Adjusted for quantity
                                style = Typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (index < cartItems.value.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 4.dp),
                                color = colorResource(R.color.gray_300),
                                thickness = 1.dp
                            )
                        }
                    }

                    // Add total section
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.total_nutrisi),
                        fontWeight = FontWeight.Bold,
                        style = Typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Display totals for each nutrient
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("Protein: $totalProtein gr", style = Typography.bodyMedium)
                        HorizontalDivider()
                        Text("Carbs: $totalCarb gr", style = Typography.bodyMedium)
                        HorizontalDivider()
                        Text("Sugar: $totalSugar gr", style = Typography.bodyMedium)
                        HorizontalDivider()
                        Text("Fat: $totalFat gr", style = Typography.bodyMedium)
                    }
                }

            }
        }

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(R.string.total_kalori) + ": $totalCalories",
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.padding(4.dp))

                CustomFillButton(
                    onClick = { /* Handle Save action */ },
                    text = stringResource(R.string.simpan),

                )
            }
        }

    }
}


@Composable
fun CartItemLayout(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onDelete: () -> Unit
) {
    // State to track if the counter should be visible
    var isRemoved by remember { mutableStateOf(false) }

    LaunchedEffect(isRemoved) {
        if (isRemoved) {
            delay(500)
            onDelete()
        }
    }

    // Animated visibility wrapper
    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = 500),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        Card(
            modifier = Modifier

                .fillMaxWidth(),
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
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .fillMaxWidth(0.5f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                style = Typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                text = cartItem.food.name,

                                )
                            Text(
                                style = Typography.bodySmall,
                                text = cartItem.food.desc,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(top = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Card(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            shape = RoundedCornerShape(20.dp),
                        ) {
                            AsyncImage(
                                model = cartItem.food.photo,
                                contentDescription = cartItem.food.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${cartItem.food.calories} Cal",
                            style = Typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        CounterCart(
                            quantity = remember { mutableIntStateOf(cartItem.quantity) },
                            onIncreaseQuantity = onIncreaseQuantity,
                            onDecreaseQuantity = onDecreaseQuantity,
                            onRemove = { isRemoved = true }
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun CounterCart(
    quantity: MutableIntState,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 2.dp)
            .height(42.dp)
    ) {
        IconButton(
            onClick = {
                onDecreaseQuantity()
                if (quantity.intValue > 1) {
                    quantity.intValue--
                } else if (quantity.intValue == 1) {
                    onRemove()
                }
            }
        ) {
            Icon(
                painterResource(R.drawable.round_remove_24),
                contentDescription = "Decrease"
            )
        }

        Box(modifier = Modifier.width(40.dp)) {
            Text(
                text = quantity.intValue.toString(),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleMedium
            )
        }

        IconButton(
            onClick = {
                onIncreaseQuantity()
                quantity.intValue++
            }
        ) {
            Icon(
                painterResource(R.drawable.rounded_add_2_24),
                contentDescription = "Increase"
            )
        }
    }
}

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    val isRemoved = remember { mutableStateOf(false) }
    val swipeDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                isRemoved.value = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(isRemoved.value) {
        if (isRemoved.value) {
            delay(animationDuration.toLong())
            swipeDismissState.reset()
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved.value,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = swipeDismissState,
            backgroundContent = {
                DeleteBackground(swipeDismissState)
            },
            content = { content(item)},
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false
        )

    }
}

@Composable
fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState,
) {
    val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        MaterialTheme.colorScheme.error
    } else {
        Color.Transparent
    }

    Card {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painter = painterResource(R.drawable.round_delete_24),
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun CartItemLayoutPreview() {
    BinaSehatTheme(darkTheme = false) {
        CartItemLayout(
            cartItem = CartItem(
                food = FakeData.fakeFoodData[0],
                quantity = 1
            ),
            onIncreaseQuantity = {},
            onDecreaseQuantity = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun CartScreenPreview() {
    BinaSehatTheme(darkTheme = false) {
        CartScreen(navController = NavController(LocalContext.current))
    }
}