### **Weather Stylist – Smart Outfit Recommendations**

#### **Project Summary**

Weather Stylist is an innovative Android application designed to solve the daily dilemma of choosing the right outfit. By leveraging real-time weather data for the user's current location, the app provides personalized clothing recommendations based on a virtual wardrobe created and managed by the user. The project's main goal is to offer a practical, daily-use tool while exploring a wide range of native Android features, including camera integration, location services, and local data persistence.

---

### **Core Functionalities**

1.  **Dynamic & Localized Weather Forecast**
    * The application will automatically detect the user's current location using GPS.
    * It will fetch and display current weather conditions and a daily forecast from a reliable third-party API (e.g., OpenWeatherMap).
    * Displayed data will include temperature, "feels like" temperature, weather conditions (e.g., Sunny, Cloudy, Rain), chance of precipitation, and wind speed.

2.  **Virtual Wardrobe Management**
    * Users can build a digital catalog of their actual clothes.
    * **Add Item:** Users can add a new clothing item by taking a picture with the device's camera.
    * **Categorize & Tag:** Each item will be saved with a user-defined category (e.g., T-shirt, Winter Jacket, Jeans, Shoes) and descriptive tags (e.g., `waterproof`, `warm`, `formal`, `cotton`, `lightweight`). These tags are crucial for the recommendation algorithm.
    * Users can view, edit, and delete items in their virtual wardrobe.

3.  **Smart Outfit Recommendations**
    * This is the core feature of the application.
    * Based on the current weather data, a simple algorithm will scan the user's virtual wardrobe.
    * It will select and suggest a suitable combination of clothing items. For example:
        * If the temperature is below 5°C, it will look for items tagged as `warm`.
        * If there is a high chance of rain, it will prioritize items tagged as `waterproof`.
    * The final recommendation will be presented to the user as a complete outfit suggestion for the day.

4.  **Proactive Daily Notifications**
    * The app will send a scheduled push notification every morning.
    * The notification will provide a brief weather summary and a direct suggestion for what to wear, making it a proactive and useful daily assistant.

---

### **Key Mobile Features Utilized**

This project is designed to make extensive use of the unique capabilities of a mobile device:

* **Location Services (GPS):** To provide an automatic and accurate weather forecast without requiring manual input from the user.
* **Camera API:** To serve as the primary input method for populating the user's virtual wardrobe, creating an interactive and engaging experience.
* **Local Storage (Room Database):** To persistently store the user's wardrobe on the device, ensuring data is available offline and managed efficiently.
* **Networking (Retrofit & OkHttp):** To handle asynchronous communication with the external weather API to fetch real-time data.
* **System Notifications:** To proactively engage the user with timely and relevant information (the daily outfit suggestion).

### **Target Platform & Technology Stack**

* **Platform:** Android
* **Language:** Kotlin
* **Key Libraries & Components:**
    * Android SDK
    * Room Persistence Library (for local database)
    * Retrofit (for networking)
    * Kotlin Coroutines (for managing background tasks)
    * CameraX / Camera2 API (for camera integration)
