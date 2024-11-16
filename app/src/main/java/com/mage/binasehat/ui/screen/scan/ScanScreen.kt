package com.mage.binasehat.ui.screen.scan

import android.Manifest
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.tv.material3.Text
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mage.binasehat.R
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.CameraX
import com.mage.binasehat.ui.screen.components.LoadingDialog
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.theme.Typography
import com.mage.binasehat.ui.util.ImageUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(
    navController: NavController,
    viewModel: FoodPredictViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val imageUriState = remember { mutableStateOf<String?>(null) }
    val isScreenActive = remember { mutableStateOf(true) }
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(Manifest.permission.CAMERA)
    )
    var showLoadingDialog by remember { mutableStateOf(false) }


    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
        }
    }

    // Create a temporary file for the photo
    val photoFile = remember {
        File(
            context.cacheDir,
            "photo_${System.currentTimeMillis()}.jpg"
        )
    }

    // Configure image capture options
    val imageOptions = remember {
        ImageCapture.OutputFileOptions.Builder(photoFile)
            .build()
    }

    DisposableEffect(navController) {
        onDispose {
            isScreenActive.value = false
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                imageUriState.value = it.toString()
                Log.d("AccountDetailScreen", "Selected image URI: $it")
                val image = ImageUtility.uriToFile(uri, context)
                if (image != null) {
                    viewModel.predictImage(image)
                    showLoadingDialog = true
                    navController.navigate("cart")
                } else {
                    Log.e("AccountDetailScreen", "Failed to convert URI to File")
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (!permissionState.allPermissionsGranted) {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    if (permissionState.allPermissionsGranted) {
        CameraWithCleanup(
            controller = controller,
            isActive = isScreenActive.value,
            onCleanup = {
                controller.unbind()
            },
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Back Button at the top left
                    BackButton(
                        modifier = Modifier
                            .padding(top = 32.dp, start = 32.dp),
                        onClick = {
                            navController.popBackStack()
                        },
                        icon = painterResource(R.drawable.round_arrow_back_ios_24),
                        text = stringResource(R.string.kembali)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    // Floating Action Button (FAB) at the top right
                    ExtendedFloatingActionButton(
                        onClick = {
                            isScreenActive.value = false  // Trigger camera cleanup
                            navController.navigate("foodList") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.rounded_add_2_24),
                                contentDescription = "Add",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(R.string.tambah_makanan_manual),
                                style = Typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        },
                        modifier = Modifier
                            .padding(top = 32.dp, end = 16.dp),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp)) // Space for top padding (optional)

                // Center area for drawables, with weight to fill remaining space
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(R.drawable.top_left),
                            contentDescription = null,
                            modifier = Modifier.size(96.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.top_right),
                            contentDescription = null,
                            modifier = Modifier.size(96.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(R.drawable.bottom_left),
                            contentDescription = null,
                            modifier = Modifier.size(96.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.bottom_right),
                            contentDescription = null,
                            modifier = Modifier.size(96.dp)
                        )
                    }
                }

                // Bottom row with Camera and Gallery buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = {
                        showLoadingDialog = true
                        galleryLauncher.launch("image/*")
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.round_image_24),
                            contentDescription = "Open Gallery",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        showLoadingDialog = true
                        controller.takePicture(
                            imageOptions,
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                    Log.d("ScanScreen", "Image saved successfully")
                                    viewModel.predictImage(photoFile)
                                    navController.navigate("cart")
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    Log.e("ScanScreen", "Photo capture failed: ${exception.message}", exception)
                                    showLoadingDialog = false
                                    Toast.makeText(
                                        context,
                                        "Failed to capture photo: ${exception.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        )

                    }) {
                        Icon(
                            painter = painterResource(R.drawable.round_photo_camera_24),
                            contentDescription = "Take Photo",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    } else {
        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
    }

    if (showLoadingDialog) {
        LoadingDialog(
            onDismiss = { showLoadingDialog = false }
        )
    }

    val predictionResult by viewModel.predictResult.collectAsState()
    predictionResult.let {
        // Display the prediction result (e.g., show the food name)
        Log.d("ScanScreen", "Prediction Result: $it")
        showLoadingDialog = false
    }
}

@Composable
fun CameraWithCleanup(
    controller: LifecycleCameraController,
    isActive: Boolean,
    onCleanup: () -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowCamera by remember { mutableStateOf(true) }

    // Handle cleanup when camera should be hidden
    LaunchedEffect(isActive) {
        if (!isActive) {
            // Small delay before hiding camera
            delay(2000)
            shouldShowCamera = false
            onCleanup()
        }
    }

    // Only show camera if both conditions are met
    if (isActive && shouldShowCamera) {
        CameraX(
            controller = controller,
            modifier = modifier
        )
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ScanScreenPreview() {
    BinaSehatTheme(darkTheme = false) {
        ScanScreen(navController = NavController(LocalContext.current))
    }
}


