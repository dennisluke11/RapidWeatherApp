package com.example.rapidweatherapp.ui.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.rapidweatherapp.ui.weather.componets.CurrentWeatherCard
import com.example.rapidweatherapp.ui.weather.componets.ErrorView
import com.example.rapidweatherapp.ui.weather.componets.LoadingView
import com.example.rapidweatherapp.ui.weather.componets.PastWeekWeatherCard
import com.example.rapidweatherapp.utils.Dimens

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.PaddingMedium),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> LoadingView()

            uiState.errorMessage != null -> ErrorView(uiState.errorMessage!!) {
                viewModel.loadCityFromLocation()
            }

            uiState.weather != null -> {
                val weather = uiState.weather!!
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CurrentWeatherCard(weather, onSearch = { cityName ->
                        viewModel.searchWeatherByCityName(cityName)
                    })

                    PastWeekWeatherCard(weather)
                }
            }

            else -> {
                Button(onClick = { viewModel.loadCityFromLocation() }) {
                    Text("Load Weather")
                }
            }
        }
    }
}


