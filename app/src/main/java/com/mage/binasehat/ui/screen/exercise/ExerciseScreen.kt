package com.mage.binasehat.ui.screen.exercise

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.mage.binasehat.R
import com.mage.binasehat.ui.screen.components.AppBar
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            stickyHeader {
                AppBar(navController)
            }

            item {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hari ini mau olahraga apa?",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                            modifier = Modifier
                                .fillMaxHeight()
                                .clickable {
                                    navController.navigate("workoutMainScreen") {
                                        popUpTo("workoutMainScreen") {
                                            inclusive = true
                                        }
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.exercise_image_home),
                                        contentDescription = "Exercise Image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                    // Dark overlay
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .background(Color.Black.copy(alpha = 0.5f))
                                    )
                                    Row(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(R.string.kebugaran),
                                            style = Typography.titleLarge.copy(color = Color.White),
                                        )
                                        Icon(
                                            painter = painterResource(R.drawable.round_arrow_forward_24),
                                            contentDescription = "Forward Icon",
                                            tint = Color.White,
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                            modifier = Modifier
                                .fillMaxHeight()
                                .clickable {
                                    navController.navigate("runningMainScreen") {
                                        popUpTo("runningMainScreen") {
                                            inclusive = true
                                        }
                                    }
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()

                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.running_image_home),
                                        contentDescription = "Running Image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                    // Dark overlay
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .background(Color.Black.copy(alpha = 0.5f))
                                    )
                                    // Title at the bottom start of the image
                                    Row(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(R.string.lari),
                                            style = Typography.titleLarge.copy(color = Color.White),
                                        )
                                        Icon(
                                            painter = painterResource(R.drawable.round_arrow_forward_24),
                                            contentDescription = "Forward Icon",
                                            tint = Color.White,
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ExerciseScreenPreview() {
    BinaSehatTheme(
        darkTheme = false,
    ) {
        ExerciseScreen(navController = NavController(LocalContext.current))
    }
}