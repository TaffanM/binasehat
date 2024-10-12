package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.Typography
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DateSlider(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit
) {
    var currentWeek by remember { mutableStateOf(generateWeekDates()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { currentWeek = generateWeekDates(currentWeek.first().minusWeeks(1)) }) {
                Icon(painter = painterResource(id = R.drawable.rounded_chevron_backward_24), contentDescription = "Previous week", tint = colorResource(R.color.green_primary))
            }
            Text(
                text = "${currentWeek.first().month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentWeek.first().year}",
                style = Typography.titleMedium
            )
            IconButton(onClick = { currentWeek = generateWeekDates(currentWeek.first().plusWeeks(1)) }) {
                Icon(painter = painterResource(id = R.drawable.rounded_chevron_forward_24), contentDescription = "Next week", tint = colorResource(R.color.green_primary))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            itemsIndexed(currentWeek) {_, date ->
                DateItem(
                    date = date,
                    isSelected = date == selectedDate,
                    isCurrentDate = date == LocalDate.now(),
                    onClick = {
                        selectedDate = date
                        onDateSelected(date)
                    }
                )
            }
        }
    }
}

@Composable
fun DateItem(
    date: LocalDate,
    isSelected: Boolean,
    isCurrentDate: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 38.dp, height = 56.dp)
            .clip(RoundedCornerShape(10))
            .clickable(onClick = onClick)
            .border(
                width = if (isCurrentDate && !isSelected) 2.dp else 0.dp,
                color = if (isCurrentDate && !isSelected) colorResource(R.color.green_primary) else Color.Transparent,
                shape = RoundedCornerShape(10)
            )
            .background(if (isSelected) colorResource(R.color.green_primary) else Color.Transparent)


    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),

        ) {
            Text(
                text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).uppercase(),
                style = Typography.bodySmall,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = date.dayOfMonth.toString(),
                style = Typography.bodySmall,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(24.dp)
            )
        }
    }
}


private fun generateWeekDates(startDate: LocalDate = LocalDate.now()): List<LocalDate> {
    val monday = if (startDate.dayOfWeek == DayOfWeek.SUNDAY) {
        startDate.minusDays(7)
    } else {
        startDate.with(DayOfWeek.MONDAY)
    }
    return (0..6).map { monday.plusDays(it.toLong()) }
}

@Preview(showBackground = true)
@Composable
fun DateSliderPreview() {
    DateSlider(
        modifier = Modifier.padding(16.dp),
        onDateSelected = {}
    )
}