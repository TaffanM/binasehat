package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mage.binasehat.R
import com.mage.binasehat.data.model.Exercise
import com.mage.binasehat.data.remote.response.ExerciseData

@Composable
fun ExerciseGridCard(exercise: Exercise, navigateToDetail: (Long) -> Unit, modifier: Modifier = Modifier) {

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
            .width(166.dp)
            .height(197.dp).clickable {
                navigateToDetail(exercise.id?.toLong() ?: 0)
            }
    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(exercise.photoUrl)
//                .crossfade(true)
//                .build(),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxWidth().height(104.dp),
//        )
        Column(

            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(12.dp),
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${exercise.category?.name} ",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (i in 1..3) {
                        Icon(
                            painter = if (i <= (exercise.level ?: 0)) painterResource(R.drawable.bolt) else painterResource(R.drawable.bolt),
                            contentDescription = null,
                            tint = if (i <= (exercise.level ?: 0)) MaterialTheme.colorScheme.primary else Color.White,
                            modifier = Modifier.size(19.dp)
                        )
                    }
                }
            }

            Text(
                text = exercise.name ?: "Exercise name",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Informasi Tambahan
                ExerciseAdditionInformation(
                    icon = painterResource(R.drawable.fire),
                    text = stringResource(id = R.string.calori_format, exercise.calEstimation ?: 0),
                    iconTint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Rating
                ExerciseAdditionInformation(
                    icon = painterResource(R.drawable.round_star_24),
                    text = stringResource(id = R.string.rating_format, exercise.rating ?: 0),
                    iconTint = MaterialTheme.colorScheme.primary
                )
            }

        }
    }
}