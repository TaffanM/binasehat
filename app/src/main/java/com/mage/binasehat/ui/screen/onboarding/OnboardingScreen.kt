package com.mage.binasehat.ui.screen.onboarding

import android.app.Activity
import android.view.View
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.BinaSehatTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val window = (context as Activity).window

    LaunchedEffect(Unit) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    DisposableEffect(Unit) {
        onDispose {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }



    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    val currentPage = pagerState.currentPage

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                onClick = {
                    coroutineScope.launch {
                        navController.navigate("login") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                SkipButton()
            }
        }
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->

            PagerScreen(
                onBoardingPage = pages[position],
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            DotsIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                totalDots = pages.size,
                selectedIndex = currentPage,
                selectedColor = colorResource(R.color.green_primary),
                unSelectedColor = colorResource(R.color.gray_300)
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (currentPage < pages.size - 1) {
                            pagerState.animateScrollToPage(
                                currentPage + 1,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearEasing
                                )
                            )
                        } else {
                            navController.navigate("login") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.green_primary),
                    contentColor = colorResource(R.color.white_bg)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.next),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }


    }
}


@Composable
fun PagerScreen(
    onBoardingPage: OnBoardingPage,
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.5f),
                painter = painterResource(onBoardingPage.image),
                contentDescription = "Image"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(onBoardingPage.desc),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )

        }
    }
}

@Composable
fun SkipButton() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.skip),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Icon(
            painter = painterResource(R.drawable.rounded_chevron_forward_24),
            modifier = Modifier,
            contentDescription = "Next"
        )
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color
) {
    LazyRow(
        modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(bottom = 8.dp, top = 32.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(selectedColor)

                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)


                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }

}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun OnboardingScreenPreview() {
    BinaSehatTheme {
        OnboardingScreen(rememberNavController())
    }
}
