package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mage.binasehat.R
import com.mage.binasehat.ui.model.NavigationItem

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme


    NavigationBar(
        containerColor = colorScheme.surface,
        contentColor = colorScheme.onSurface,
        tonalElevation = 1.dp,
        modifier = modifier
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.beranda),
                icon = if (currentRoute == "home") R.drawable.home_active else R.drawable.home_inactive,
                route = "home"
            ),
            NavigationItem(
                title = stringResource(R.string.makanan),
                icon = if (currentRoute == "food") R.drawable.food_active else R.drawable.food_inactive,
                route = "food"
            ),
            NavigationItem(
                title = stringResource(R.string.olahraga),
                icon = R.drawable.exercise,
                route = "exercise"
            ),
        )

        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                label = {
                    Text(
                        text = item.title,
                        color = if (currentRoute == item.route) colorResource(R.color.green_primary) else colorScheme.onSurface
                    )
                },
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.title,
                            modifier = Modifier.size(23.5.dp)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                        indicatorColor = colorScheme.primary,
                        selectedIconColor = colorScheme.surface,
                    )
            )
        }

    }

}