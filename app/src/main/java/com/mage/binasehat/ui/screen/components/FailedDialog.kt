package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.tv.material3.Text
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.Typography

@Composable
fun FailedDialog(
    onDismiss: () -> Unit,
    text: String
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.check))
    val progress by animateLottieCompositionAsState(composition)

    Dialog(
        onDismissRequest = onDismiss,
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(300.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center, // Add this
                modifier = Modifier.padding(16.dp) // Optional padding
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .size(150.dp) // Set a fixed size for the animation
                )
                Spacer(modifier = Modifier.height(16.dp)) // Add some space between animation and button
                Text(
                    text = text,
                    color = Color.Black,
                    style = Typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp)) // Add some space between text and button
                CustomFillButton(
                    text = "OK",
                    onClick = onDismiss,
                    modifier = Modifier
                )
            }
        }
    }
}