package com.example.rapidweatherapp.ui.weather.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.example.rapidweatherapp.data.remote.ForecastDay
import com.example.rapidweatherapp.utils.Dimens

@Composable
fun PastWeatherItem(day: ForecastDay) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(day.date, style = MaterialTheme.typography.bodyMedium)

        Row(verticalAlignment = Alignment.CenterVertically) {
            val dayIcon = "https:${day.day.condition.icon}"
            Image(
                painter = rememberAsyncImagePainter(dayIcon),
                contentDescription = day.day.condition.text,
                modifier = Modifier.size(Dimens.largeImageExtraSmall)
            )
            Spacer(modifier = Modifier.width(Dimens.SpacerHeightSmall))
            Text("${day.day.avgtemp_c}°C", style = MaterialTheme.typography.bodyMedium)
        }
    }
}