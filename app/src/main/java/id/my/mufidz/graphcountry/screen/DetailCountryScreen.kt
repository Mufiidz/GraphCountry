package id.my.mufidz.graphcountry.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.my.mufidz.graphcountry.component.LoadingScreen
import id.my.mufidz.graphcountry.intention.DetailCountryAction
import id.my.mufidz.graphcountry.model.Countries
import id.my.mufidz.graphcountry.model.Country
import id.my.mufidz.graphcountry.model.CountryInfo
import id.my.mufidz.graphcountry.viewmodel.DetailCountryViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailCountryScreen(country: Countries, sheetState: BottomSheetState, onDismiss: () -> Unit) {
    val viewModel = hiltViewModel<DetailCountryViewModel>()
    viewModel.execute(DetailCountryAction.GetDetailCountry(country.code))

    val state = viewModel.viewState.collectAsState().value

    val isToBottom = sheetState.targetValue == BottomSheetValue.Collapsed

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Handle()
        AnimatedVisibility(isToBottom) {
            DetailCountryPeekContent(country, onDismiss)
        }
        LoadingScreen(state.isLoading) {
            DetailCountryContent(state.country)
        }
    }
}

@Composable
fun Handle() {
    Box(Modifier.padding(vertical = 10.dp, horizontal = 8.dp)) {
        Box(
            Modifier
                .width(32.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}

@Composable
private fun DetailCountryContent(
    country: Country = Country(
        "", "INDO", "", "JAK", "RP", listOf(), ""
    ),
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = country.emoji, fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = country.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(text = country.capital, fontSize = 15.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(country.toListCountryInfo()) {
                ItemCountryInfo(it)
            }
        }
    }
}

@Composable
private fun ItemCountryInfo(countryInfo: CountryInfo = CountryInfo("TITLE", "CONTENT")) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = countryInfo.title, modifier = Modifier.fillMaxWidth(0.4f))
        Text(text = ":", modifier = Modifier.padding(horizontal = 4.dp))
        Text(text = countryInfo.content, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun DetailCountryPeekContent(country: Countries = Countries(), onDismiss: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .height(60.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = country.emoji, fontSize = 30.sp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = country.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = country.capital, maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Clear, contentDescription = "Dismiss BottomSheet")
        }
    }
}