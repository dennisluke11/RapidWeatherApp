package com.example.rapidweatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.rapidweatherapp.ui.theme.WeatherTaskAppTheme
import com.example.rapidweatherapp.ui.weather.WeatherScreen
import com.example.rapidweatherapp.ui.weather.WeatherViewModel
import com.example.rapidweatherapp.ui.weather.componets.LoadingView
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val weatherViewModel: WeatherViewModel = getViewModel()
            val weatherUiState by weatherViewModel.uiState.collectAsState()

            var hasLocationPermission by remember { mutableStateOf(false) }
            var isLocationServiceEnabled by remember { mutableStateOf(false) }
            var permissionRequested by remember { mutableStateOf(false) }

            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                hasLocationPermission = isGranted
                if (isGranted) {
                    weatherViewModel.loadCityFromLocation()
                }
            }

            LaunchedEffect(Unit) {
                val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                isLocationServiceEnabled =
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

                val permissionCheck = ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                hasLocationPermission = permissionCheck == PackageManager.PERMISSION_GRANTED

                if (isLocationServiceEnabled && hasLocationPermission) {
                    weatherViewModel.loadCityFromLocation()
                } else if (!hasLocationPermission && !permissionRequested) {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    permissionRequested = true
                }
            }

            WeatherTaskAppTheme(darkTheme = weatherUiState.isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            !isLocationServiceEnabled || !hasLocationPermission -> {
                                Text(
                                    text = "Weather is not available for now until you enable the location.",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(24.dp)
                                )
                            }

                            weatherUiState.isLoading -> {
                                LoadingView()
                            }

                            weatherUiState.weather != null -> {
                                Column(
                                    modifier = Modifier
                                        .padding(vertical = 16.dp, horizontal = 8.dp)
                                        .fillMaxSize()
                                ) {

                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                    ) {
                                        WeatherScreen(viewModel = weatherViewModel)
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 16.dp),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        IconButton(onClick = { weatherViewModel.loadCityFromLocation() }) {
                                            Icon(
                                                modifier = Modifier.size(50.dp),
                                                imageVector = Icons.Default.Refresh,
                                                contentDescription = "Refresh"
                                            )
                                        }
                                    }
                                }


                            }

                            weatherUiState.errorMessage != null -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Error: ${weatherUiState.errorMessage}",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(onClick = {
                                        weatherViewModel.loadCityFromLocation()
                                    }) {
                                        Text(stringResource(R.string.try_again))
                                    }
                                }
                            }

                            else -> {
                                Text(stringResource(R.string.loading))
                            }
                        }
                    }
                }
            }
        }
    }
}