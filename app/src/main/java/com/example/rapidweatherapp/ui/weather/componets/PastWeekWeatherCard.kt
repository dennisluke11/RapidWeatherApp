package com.example.rapidweatherapp.ui.weather.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.rapidweatherapp.R
import com.example.rapidweatherapp.data.remote.WeatherResponse
import com.example.rapidweatherapp.utils.Dimens

@Composable
fun PastWeekWeatherCard(weather: WeatherResponse) {
    val pastDays = weather.forecast.forecastday

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(Dimens.PaddingMedium)) {
            Text(
                text = stringResource(R.string.past_days_weather),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = Dimens.PaddingSmall)
            )

            if (pastDays.isEmpty()) {
                Text(stringResource(R.string.no_past_data))
            } else {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(Dimens.SpacerHeightSmall)
                ) {
                    items(pastDays) { day ->
                        PastWeatherItem(day)
                    }
                }
            }
        }
    }
}