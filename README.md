# RapidWeatherApp ☀️🌧️

RapidWeatherApp is a modern Android weather application built with **Jetpack Compose**, **Kotlin**, and **MVVM architecture**. It fetches the current and past week's weather data using location services, offering a clean, responsive UI and handling permissions gracefully.

## 📱 Features

- 🌍 Fetch weather based on current location
- 🔍 Search weather by city name
- 🌦️ View current and past 7 days of weather forecasts
- 📉 click refresh button for live weather updates
- 🔒 Handles location permission and GPS enablement
- 🎨 Dark/light theme toggle support via ViewModel state

## 🖼 UI Screens
-CurrentWeatherCard
-Displays today's temperature, icon, condition, and city.
-PastWeekWeatherCard
-Shows a scrollable list of average temperatures and conditions for the last 7 days.
-Error / Loading States
-Intuitive UI messaging with retry capability.

## 🚀 Getting Started

### Prerequisites

- Android Studio Giraffe or newer
- Minimum SDK: 24
- Kotlin 1.8+
- Internet connection (for fetching weather data)
- A valid Weather API key

### ⚙️ Setup Instructions
- Clone this repo(git clone https://github.com/your-username/rapidweatherapp.git)
- Open in Android Studio
- Set API Key
- In your FlowConstants.kt, replace with your Weather API key:(const val API_KEY = "your_api_key_here")
- Run the app

Build and install on a device or emulator with location services enabled.

## 🧩 TODO / Future Improvements
- 📌 Cache weather data offline
- 🌈 Improve UI responsiveness on tablets and foldables
