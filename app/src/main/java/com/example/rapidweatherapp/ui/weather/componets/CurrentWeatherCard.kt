package com.example.rapidweatherapp.ui.weather.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.rapidweatherapp.R
import com.example.rapidweatherapp.data.remote.WeatherResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CurrentWeatherCard(weather: WeatherResponse?, onSearch: (String) -> Unit) {
    if (weather == null) return

    val current = weather.current
    val location = weather.location
    val iconUrl = "https:${current.condition.icon}"

    val today = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()).format(Date())

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchInput(
                onSearch = onSearch,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$today",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.temperature),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${current.temp_c}°C",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
            Text(
                text = current.condition.text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            Image(
                painter = rememberAsyncImagePainter(iconUrl),
                contentDescription = current.condition.text,
                modifier = Modifier.size(130.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "${location.name}, ${location.country}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}