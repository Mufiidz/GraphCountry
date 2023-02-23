package id.my.mufidz.graphcountry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ModalBottomSheetValue.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import id.my.mufidz.graphcountry.model.Countries
import id.my.mufidz.graphcountry.screen.DetailCountryScreen
import id.my.mufidz.graphcountry.screen.HomeScreen
import id.my.mufidz.graphcountry.ui.theme.GraphCountryTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphCountryTheme {
                val coroutineScope = rememberCoroutineScope()
                val sheetState = rememberBottomSheetState(
                    initialValue = BottomSheetValue.Collapsed,
                    animationSpec = spring(Spring.DampingRatioLowBouncy)
                )
                val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

                var country by remember { mutableStateOf(Countries()) }
                var isDismissSheet by remember { mutableStateOf(true) }
                val peekHeight = if (isDismissSheet) 0.dp else 89.dp

                val roundedCornerRadius =
                    if (sheetState.targetValue == BottomSheetValue.Expanded) 0.dp else 28.dp

                BackHandler(sheetState.isExpanded) {
                    coroutineScope.launch { sheetState.collapse() }
                }
                BottomSheetScaffold(scaffoldState = scaffoldState,
                    sheetShape = RoundedCornerShape(
                        topStart = roundedCornerRadius, topEnd = roundedCornerRadius
                    ),
                    sheetPeekHeight = peekHeight,
                    sheetElevation = 15.dp,
                    sheetBackgroundColor = MaterialTheme.colorScheme.surface,
                    sheetContent = {
                        DetailCountryScreen(
                            country = country, sheetState = sheetState
                        ) { isDismissSheet = true }
                    }) {
                    HomeScreen {
                        if (it.code.isNotEmpty()) {
                            country = it
                            isDismissSheet = false
                            coroutineScope.launch {
                                sheetState.expand()
                            }
                        }
                    }
                }
            }
        }
    }
}