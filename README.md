# Weatherly

A simple weather application for Android, built with modern Android development tools. It allows users to get weather information for their current location or search for other locations.

## Features

*   **Current Weather:** Displays the current temperature in Celsius, a descriptive summary of the weather conditions (e.g., "Sunny," "Partly cloudy"), and a corresponding icon that visually represents the current weather. This information is prominently displayed at the top of the screen for immediate visibility.
*   **Hourly Forecast:** Provides a horizontally scrollable list of the weather forecast for the next 24 hours. Each item in the forecast shows the time, a weather icon, and the predicted temperature, allowing users to easily plan their day.
*   **Air Quality:** Shows the current Air Quality Index (AQI) based on the CPCB standard. It includes a color-coded indicator and a brief description of the air quality level, helping users understand the potential health implications.
*   **Wind and Atmosphere:** Presents detailed information about the wind, including its speed in kilometers per hour and its direction, along with the current atmospheric pressure in millibars. This gives users a more complete picture of the current weather conditions.
*   **Location Search:** Allows users to search for weather information for any city worldwide. As the user types, a list of possible locations is suggested. When a location is selected, the app fetches and displays the weather for that area.
*   **Current Location:** With the user's permission, the app can automatically detect their current location using the device's GPS and display the local weather information. This provides a quick and convenient way for users to get the weather for their immediate surroundings.
*   **Favorite Locations:** Users can save their most frequently checked locations as favorites. These locations are stored locally on the device using a Room database and can be easily accessed from a dedicated screen, allowing for quick switching between different weather forecasts.
*   **Home Screen Widget:** A simple and convenient home screen widget that displays the current weather for a selected favorite location. This allows users to get a quick glance at the weather without having to open the app.

## Technologies Used

*   **UI (Jetpack Compose):** The entire user interface is built with Jetpack Compose, a modern, declarative UI toolkit for Android. This allows for the creation of beautiful, reactive UIs with less code compared to the traditional XML-based approach.
*   **Architecture (MVVM):** The app follows the Model-View-ViewModel (MVVM) architectural pattern. This separates the UI (View) from the business logic (ViewModel) and the data (Model), leading to a more organized, testable, and maintainable codebase.
*   **Dependency Injection (Hilt):** Hilt, a dependency injection library built on top of Dagger, is used to manage dependencies throughout the application. It simplifies the process of providing dependencies to Android framework classes and reduces boilerplate code.
*   **Networking (Retrofit):** Retrofit is used as a type-safe HTTP client to handle network requests to the external weather and geocoding APIs. It simplifies the process of fetching data from the web by turning the API endpoints into Kotlin interfaces.
*   **JSON Parsing (Gson):** Gson is integrated with Retrofit to parse the JSON responses from the APIs into Kotlin data classes. This allows for easy and type-safe access to the received data.
*   **Database (Room):** The Room persistence library, an abstraction layer over SQLite, is used to store and manage the user's favorite locations. Room provides compile-time verification of SQL queries and simplifies database interactions.
*   **Location Services (Google Play Services):** The app uses the Fused Location Provider API from Google Play Services to efficiently retrieve the device's last known location for displaying local weather.
*   **Image Loading (Coil):** Coil, an image loading library backed by Kotlin Coroutines, is used to download and display the weather icons from the Weather API. It provides a simple and efficient way to handle image loading and caching.
*   **Widgets (Glance):** The home screen widget is built using Glance, a modern framework for creating app widgets with Jetpack Compose-like APIs. This simplifies the development of responsive and interactive widgets.

## External APIs

*   **Weather API ([WeatherAPI.com](https://www.weatherapi.com/)):** Used to retrieve all weather-related data.
    *   **Endpoint:** `GET /forecast.json`
    *   **Request:** The app makes a GET request to the endpoint with the API key, location query (`q`), number of forecast days (`days`), and an air quality data flag (`aqi=yes`).
        *   *Example: `GET /forecast.json?key=YOUR_API_KEY&q=London&days=1&aqi=yes`*
    *   **Response:** The API returns a JSON object containing `location` details, `current` weather conditions (including temperature, condition text/icon, wind, pressure, and air quality), and a `forecast` object with a day-by-day and hour-by-hour breakdown.

*   **Geocoding API ([OpenCage Geocoding API](https://opencagedata.com/)):** Used for both **direct (forward)** and **reverse** geocoding to convert between location names and geographic coordinates.
    *   **Forward (Direct) Geocoding:** Used to find coordinates from a city name.
        *   **Endpoint:** `GET /direct`
        *   **Request Example:** `GET /direct?q=Paris&appid=YOUR_API_KEY`
    *   **Reverse Geocoding:** Used to find a location name from coordinates.
        *   **Endpoint:** `GET /reverse`
        *   **Request Example:** `GET /reverse?lat=48.8566&lon=2.3522&appid=YOUR_API_KEY`
    *   **Response:** The API returns a JSON object with a `results` array. Each result contains detailed components of the location (like `city`, `country`, `state`) and its `geometry` (`lat`, `lng`).

## Room Database

The application uses the Room persistence library to store and manage users' favorite locations. This allows for offline access to saved locations and a more seamless user experience.

### Database Architecture

The database is defined in the `WeatherAppDatabase` class and consists of a single entity, `Location`, which is managed by the `LocationDao`.

*   **`WeatherAppDatabase`:** The main database class, annotated with `@Database`. It serves as the main access point to the persisted data and provides an abstract method to get the `LocationDao`.
*   **`Location` Entity:** A data class annotated with `@Entity` that models the `location` table in the database. It includes fields for `id`, `latitude`, `longitude`, `name`, `country`, and `state`.
*   **`LocationDao`:** A Data Access Object (DAO) that provides an API for interacting with the `location` table. It's responsible for all data operations, such as adding, removing, and retrieving favorite locations. It uses Kotlin's `Flow` to expose a stream of data that the UI can observe, ensuring that any changes to the favorite locations are automatically reflected on the screen.

## Data Models

The main data models used in the application are:

*   `Location`: Represents a favorite location stored in the Room database.
*   `WeatherSummary`: A summary of the weather information, including current weather, hourly forecast, air quality, and wind/atmosphere data.
*   `GeocodeResponse`: Represents the response from the Geocoding API, containing location details.
*   `WeatherResponse`: Represents the response from the Weather API, containing detailed weather information.

## Setup

1.  Clone the repository:
    ```bash
    git clone https://github.com/AntuanW/android-weather-app.git
    ```
2.  Open the project in Android Studio.
3.  Create a `local.properties` file in the root of the project with the following content:
    ```properties
    api_key=YOUR_WEATHER_API_KEY
    geo_api_key=YOUR_GEOCODING_API_KEY
    base_url=https://api.weatherapi.com/v1/
    geo_base_url=https://api.opencagedata.com/geocode/v1/
    ```
    Replace `YOUR_WEATHER_API_KEY` and `YOUR_GEOCODING_API_KEY` with your actual API keys.

## Project Structure

The project is organized into the following main packages:

*   `di`: Contains the Hilt modules for dependency injection.
*   `model`: Includes data classes for API responses, Room entities, and services for accessing the network and database.
*   `view`: Contains all the Composable UI elements, including screens and reusable components.
*   `viewmodel`: Holds the `SearchWeatherViewModel` which contains the business logic and exposes the state to the UI.
*   `view/widget`: Contains the implementation for the home screen widget using Glance.

