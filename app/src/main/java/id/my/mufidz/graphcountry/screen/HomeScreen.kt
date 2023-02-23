package id.my.mufidz.graphcountry.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.my.mufidz.graphcountry.R
import id.my.mufidz.graphcountry.component.LoadingScreen
import id.my.mufidz.graphcountry.intention.HomeAction
import id.my.mufidz.graphcountry.model.Countries
import id.my.mufidz.graphcountry.viewmodel.HomeViewModel
import timber.log.Timber

@Composable
fun HomeScreen(onItemClick: (Countries) -> Unit) {
    val viewModel = hiltViewModel<HomeViewModel>()
    viewModel.execute(HomeAction.LoadCountries)

    val state = viewModel.viewState.collectAsState().value
//    Timber.d("state -> $state")
//    HomeScreenContent(
//        mapOf(
//            Pair(
//                'A',
//                listOf(Countries(), Countries(), Countries())
//            )
//        ), onItemClick
//    )
    LoadingScreen(state.isLoading) {
        HomeScreenContent(state.groupingCountries, onItemClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
private fun HomeScreenContent(
    groupingCountries: Map<Char, List<Countries>> = mapOf(
        Pair(
            'A',
            listOf(Countries(), Countries(), Countries())
        )
    ), onItemClick: (Countries) -> Unit = {}
) {
    Scaffold(Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
        )
    }) { paddingValues ->
        LazyVerticalStaggeredGrid(
            StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            contentPadding = paddingValues
        ) {
            groupingCountries.forEach { (c, countries) ->
                item(span = StaggeredGridItemSpan.FullLine) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = c.toString(), fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = countries.size.toString(),
                            fontSize = 20.sp,
                            color = Color.LightGray
                        )
                    }
                }
                items(countries) {
                    ItemCountries(it, onItemClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun ItemCountries(countries: Countries = Countries(), onItemClick: (Countries) -> Unit = {}) {
    Card(
        onClick = { onItemClick(countries) },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        shape = ShapeDefaults.Medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxSize(),
            Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = countries.emoji, fontSize = 40.sp
            )
            Text(
                text = countries.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(text = countries.capital, fontSize = 15.sp, textAlign = TextAlign.Center)
        }
    }
}