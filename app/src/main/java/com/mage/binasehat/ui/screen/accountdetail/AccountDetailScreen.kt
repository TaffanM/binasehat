package com.mage.binasehat.ui.screen.accountdetail

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mage.binasehat.R
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.BirthEditText
import com.mage.binasehat.ui.screen.components.BodyStats
import com.mage.binasehat.ui.screen.components.CustomFillButton
import com.mage.binasehat.ui.screen.components.GenderOption
import com.mage.binasehat.ui.screen.components.TextField
import com.mage.binasehat.ui.theme.BinaSehatTheme

@Composable
fun AccountDetailScreen(
    navController: NavController
) {
    val imageUriState = remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                imageUriState.value = it.toString()
                Log.d("AccountDetailScreen", "Selected image URI: $it")
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
                .verticalScroll(scrollState)
        ) {
            BackButton(
                text = stringResource(R.string.pengaturan),
                icon = painterResource(R.drawable.round_arrow_back_ios_24),
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(start = 32.dp)
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp,),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileImage(imageUriState.value, onEditClick = {
                    galleryLauncher.launch("image/*")
                })
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Card(
                    colors = CardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 36.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        TextField(
                            input = "Taffan",
                            type = stringResource(R.string.nama),
                            placeholder = stringResource(R.string.username_anda),
                            onValueChange = {},
                            focusRequester = remember { FocusRequester() },
                            onDone = {},
                            keyboardType = KeyboardType.Text
                        )
                        TextField(
                            input = "",
                            type = "Email",
                            placeholder = stringResource(R.string.email_anda),
                            onValueChange = {},
                            focusRequester = remember { FocusRequester() },
                            onDone = {},
                            keyboardType = KeyboardType.Text
                        )
                        TextField(
                            input = "",
                            type = stringResource(R.string.katasandi_baru),
                            placeholder = stringResource(R.string.masukkan_katasandi_baru),
                            onValueChange = {},
                            focusRequester = remember { FocusRequester() },
                            onDone = {},
                            keyboardType = KeyboardType.Text
                        )
                        TextField(
                            input = "",
                            type = stringResource(R.string.konfirmasi_katasandi_baru),
                            placeholder = stringResource(R.string.konfirmasi_masukkan_katasandi_baru),
                            onValueChange = {},
                            focusRequester = remember { FocusRequester() },
                            onDone = {},
                            keyboardType = KeyboardType.Text
                        )
                        GenderOption()
                        BirthEditText()
                        BodyStats()
                    }

                }
            }

        }
        CustomFillButton(
            text = stringResource(R.string.simpan),
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }

}

@Composable
fun ProfileImage(
    imageUrl: String?,
    onEditClick: () -> Unit // Callback for when the pencil button is clicked
) {
    Box(
        contentAlignment = Alignment.BottomEnd, // Align pencil icon to the bottom end
        modifier = Modifier.size(120.dp)
    ) {
        // Profile image
        val placeholder = painterResource(id = R.drawable.placeholder_image) // Your placeholder image resource
        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            placeholder = placeholder,
            error = placeholder,
            fallback = placeholder,
            onSuccess = {
                Log.d("ProfileImage", "Image loaded successfully")
            },
            onError = {
                Log.e("ProfileImage", "Error loading image: ${it.result.throwable}")
            }
        )

        Image(
            painter = painter,
            contentDescription = "Profile Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .border(4.dp, MaterialTheme.colorScheme.background, CircleShape)
        )

        // Pencil button for editing the profile image
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Set the desired elevation here
            shape = CircleShape, // Ensure the shape is a circle
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.BottomEnd) // Keep alignment if needed
        ) {
            IconButton(
                onClick = { onEditClick() }, // Trigger the callback when clicked
                modifier = Modifier
                    .size(48.dp) // Keep size consistent
                    .clip(CircleShape) // Clip to the same shape
                    .background(MaterialTheme.colorScheme.primary) // Set background color
            ) {
                Icon(
                    imageVector = Icons.Default.Edit, // Use a pencil icon here
                    contentDescription = "Edit Profile Picture",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun AccountDetailScreenPreview() {
    BinaSehatTheme(darkTheme = false) {
        AccountDetailScreen(
            navController = NavController(LocalContext.current)
        )
    }
}