package com.mage.binasehat.ui.screen.running.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mage.binasehat.R
import com.mage.binasehat.data.model.Run
import com.mage.binasehat.data.util.RunSortOrder
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.RunInfoDialog
import com.mage.binasehat.ui.screen.running.main.RunItem
import com.mage.binasehat.ui.theme.Typography
import kotlin.math.max

@Composable
fun RunningHistoryScreen(
    navController: NavController,
    viewModel: RunningHistoryViewModel = hiltViewModel()
) {
    val runItems = viewModel.runList.collectAsLazyPagingItems()


    RunningHistoryScreenContent(
        runItems = runItems,
        onSortOrderSelected = viewModel::setSortOrder,
        onItemClick = viewModel::setDialogRun,
        onNavIconClick = { navController.navigateUp() }
    )

    viewModel.dialogRun.collectAsStateWithLifecycle().value?.let {
        RunInfoDialog(
            run = it,
            onDismiss = { viewModel.setDialogRun(null) },
            onDelete = { viewModel.deleteRun() }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RunningHistoryScreenContent(
    runItems: LazyPagingItems<Run>,
    onSortOrderSelected: (RunSortOrder) -> Unit,
    onItemClick: (Run) -> Unit,
    onNavIconClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 32.dp)
        ) {
            stickyHeader {
                ScreenTopAppBar(onSortOrderSelected, onNavIconClick)
            }

            // Loading State
            item {
                if (runItems.loadState.refresh == LoadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }

            // Empty State
            item {
                if (runItems.itemCount == 0 && runItems.loadState.refresh != LoadState.Loading) {
                    EmptyRunListView()
                }
            }

            // Actual Data
            items(runItems.itemCount) { index ->
                runItems[index]?.let { run ->
                    RunCardItem(run = run, onItemClick = onItemClick)
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun ScreenTopAppBar(
    onSortOrderSelected: (RunSortOrder) -> Unit = {},
    onNavIconClick: () -> Unit = {}
) {
    var isDropDownExpended by rememberSaveable { mutableStateOf(false) }
    val sortOrderList = remember { RunSortOrder.entries }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        BackButton(
            onClick = onNavIconClick,
            icon = painterResource(R.drawable.round_arrow_back_ios_24),
            text = stringResource(R.string.kembali),
            modifier = Modifier.padding(start = 32.dp, top = 32.dp, bottom = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Column {
            Button(
                onClick = { isDropDownExpended = !isDropDownExpended },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(start = 32.dp, top = 32.dp, end = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.urutkan_berdasarkan),
                    style = Typography.bodySmall,
                    modifier = Modifier
                        .width(72.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "sort by",
                    tint = Color.White
                )
            }
            DropDownList(
                list = sortOrderList,
                stringTransform = {
                    it.getLocalizedString(context)
                },
                onItemSelected = {
                    onSortOrderSelected(it)
                    isDropDownExpended = false
                },
                request = { isDropDownExpended = it },
                isOpened = isDropDownExpended,

            )
        }

    }
}

//@Composable
//private fun RunningList(
//    runItems: LazyPagingItems<Run>,
//    onItemClick: (Run) -> Unit
//) {
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(bottom =  8.dp)
//    ) {
//
//        if (runItems.loadState.refresh == LoadState.Loading) item {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentWidth(Alignment.CenterHorizontally)
//            )
//        }
//        else items(runItems.itemCount) {
//            runItems[it]?.let { run ->
//                RunCardItem(run = run, onItemClick = onItemClick)
//            }
//        }
//    }
//}

@Composable
private fun RunCardItem(
    run: Run,
    onItemClick: (Run) -> Unit = {}
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .clickable { onItemClick(run) }
            .padding(vertical = 4.dp, horizontal = 32.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        RunItem(
            run = run,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun EmptyRunListView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.round_calendar_month_24),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.size(16.dp))
        val inlineContentMap = mapOf(
            "run_icon_img" to InlineTextContent(
                placeholder = Placeholder(
                    MaterialTheme.typography.bodyLarge.fontSize,
                    MaterialTheme.typography.bodyLarge.fontSize,
                    PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.rounded_directions_run_24),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }
        )
        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.belum_ada_sesi_lari))
                appendInlineContent(id = "run_icon_img")
            },
            inlineContent = inlineContentMap,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun <T : Any> DropDownList(
    modifier: Modifier = Modifier,
    list: List<T>,
    stringTransform: (T) -> String = { it.toString() },
    onItemSelected: (T) -> Unit,
    isOpened: Boolean = false,
    request: (Boolean) -> Unit,
) {
    DropdownMenu(
        modifier = modifier,
        expanded = isOpened,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringTransform(it),
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.Start)
                    )
                },
                onClick = { onItemSelected(it) },
            )
        }
    }
}