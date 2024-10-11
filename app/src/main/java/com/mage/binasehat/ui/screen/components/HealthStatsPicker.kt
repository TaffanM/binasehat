package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.Typography

@Composable
fun HeightPicker(
    initialHeight: Int,
    onHeightChange: (Int) -> Unit
) {
    var currentHeight by remember { mutableIntStateOf(initialHeight) }
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (initialHeight - 130).coerceAtLeast(0)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.tinggi_anda),
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(100.dp)
        ) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(vertical = 80.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(81) { index ->
                    val height = 130 + index
                    val isSelected = height == currentHeight

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = height.toString(),
                            style = if (isSelected) {
                                Typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 32.sp
                                )
                            } else {
                                Typography.bodyLarge.copy(
                                    color = Color.Gray,
                                    fontSize = 24.sp
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                val height = 130 + index
                currentHeight = height
                onHeightChange(height)
            }
    }
}

@Composable
fun WeightPicker(
    initialWeight: Int,
    onWeightChange: (Int) -> Unit
) {
    var currentWeight by remember { mutableIntStateOf(initialWeight) }
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (initialWeight - 20).coerceAtLeast(0)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.berat_anda),
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(100.dp)
        ) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(vertical = 80.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(300) { index ->
                    val weight = 20 + index
                    val isSelected = weight == currentWeight

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = weight.toString(),
                            style = if (isSelected) {
                                Typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 32.sp
                                )
                            } else {
                                Typography.bodyLarge.copy(
                                    color = Color.Gray,
                                    fontSize = 24.sp
                                )
                            }
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                val weight = 20 + index
                currentWeight = weight
                onWeightChange(weight)
            }
    }
}

