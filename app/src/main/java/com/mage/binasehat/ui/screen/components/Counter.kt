package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mage.binasehat.R

@Composable
fun Counter(
    quantity: MutableIntState,
) {
    // Counter
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
        IconButton(onClick = {
            if (quantity.intValue > 1) {
                quantity.intValue--
            }
        }
        ) {
            Icon(painterResource(R.drawable.round_remove_24), contentDescription = "Decrease")
        }
        Box(modifier = Modifier.width(40.dp)) { // Adjust width as necessary
            Text(
                text = quantity.intValue.toString(),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleMedium
            )
        }
        IconButton(onClick = {
            quantity.intValue++
        }) {
            Icon(painterResource(R.drawable.rounded_add_2_24), contentDescription = "Increase")
        }
    }
}
