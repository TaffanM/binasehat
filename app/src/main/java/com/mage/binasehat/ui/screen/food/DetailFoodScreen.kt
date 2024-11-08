package com.mage.binasehat.ui.screen.food

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mage.binasehat.R
import com.mage.binasehat.data.local.fake.FakeData
import com.mage.binasehat.data.model.Food
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.Counter
import com.mage.binasehat.ui.screen.components.CustomFillButton
import com.mage.binasehat.ui.theme.BinaSehatTheme

@Composable
fun DetailFoodScreen(
    navController: NavController,
    foodId: Int,
    foodViewModel: FoodViewModel = viewModel()
) {
    val food = FakeData.fakeFoodData.firstOrNull() { it.foodId == foodId }
    food?.let {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Food Image as background
            Image(
                painter = painterResource(food.photo),
                contentDescription = "Food Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp), // Make image taller to account for overlap
                contentScale = ContentScale.Crop
            )

            // Back button
            BackButton(
                text = stringResource(R.string.list_makanan),
                onClick = {
                    navController.popBackStack()
                },
                icon = painterResource(R.drawable.round_arrow_back_ios_24),
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp)
            )

            // Content Card with negative top margin for overlap
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 300.dp) // Adjust this value to control overlap
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                DetailFoodContent(
                    food = food
                ) { quantity ->
                    foodViewModel.addItem(food, quantity)
                    navController.popBackStack()
                }
            }
        }
    }

}

@Composable
fun DetailFoodContent(
    food: Food,
    onAddToCart: (quantity: Int) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            val quantity = remember { mutableIntStateOf(1) }

            // Title and Calories
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .width(225.dp)
                )
                Text(
                    text = "${food.calories} Cal",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nutrition Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NutritionInfo(stringResource(R.string.gula), food.sugar.toString())
                NutritionInfo(stringResource(R.string.protein), food.protein.toString())
                NutritionInfo(stringResource(R.string.karbohidrat), food.carb.toString())
                NutritionInfo(stringResource(R.string.lemak), food.fat.toString())
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Description
            Text(
                text = food.headline,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = food.desc,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Counter and Add Button
                Counter(
                    quantity = quantity
                )

                // Fixed width Box to ensure space
                Box(modifier = Modifier.width(16.dp))

                // Add Button
                CustomFillButton(
                    text = stringResource(R.string.tambah),
                    onClick = {
                        Toast.makeText(context, R.string.tambah_sukses, Toast.LENGTH_SHORT).show()
                        onAddToCart(quantity.intValue)
                    },
                )
            }
        }
    }
}


@Composable
fun NutritionInfo(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Preview
@Composable
fun DetailFoodScreenPreview() {
    BinaSehatTheme(darkTheme = false) {
        DetailFoodScreen(
            navController = NavController(LocalContext.current),
            foodId = 3
        )
    }
}