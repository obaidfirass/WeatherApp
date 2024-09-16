package com.example.weatherapp.cities.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.base.ErrorContent
import com.example.weatherapp.base.LoadingIndicator
import com.example.weatherapp.base.TopBar
import com.example.weatherapp.cities.domain.model.City

@Composable
fun CitiesScreenImp(
    modifier: Modifier = Modifier,
    viewModel: CitiesViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar()
        }

    ) { innerPadding ->
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.getCities()
        }

        CitiesContent(
            modifier = Modifier.padding(innerPadding),
            isLoading = state.isLoading,
            isError = state.isError ?: false,
            cities = state.items,
            onRefresh = { viewModel.getCities() }
        )
    }
}

@Composable
fun CitiesContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    cities: List<City>,
    onRefresh: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LoadingIndicator(
            isLoading = isLoading,
            isError = isError,
            onRefresh = onRefresh,
            errorContent = { ErrorContent() },
        ) {
            LazyColumn {
                itemsIndexed(cities) { index, city ->
                    CityItem(city = city)
                    if (index < cities.size - 1) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.Blue)
                                .padding(horizontal = 16.dp),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CityItem(city: City) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = city.city,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun CitiesScreenImpPreview() {
    CitiesContent(
        isLoading = true,
        isError = false,
        cities = emptyList(),
        onRefresh = {}
    )
}
