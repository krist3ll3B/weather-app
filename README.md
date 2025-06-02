# weather-app

This simple weather app integrates with WeatherStack and OpenWeather to provide weather data. It requires API keys for both services to function properly.

## Setup Instructions:
1. **Clone the repository**  
   ```sh
   git clone https://github.com/krist3ll3B/weather-app.git
   cd weather-app
2. **Configure API keys**  
   Open the src/main/resources/application.properties file and set your credentials:
   ```properties
   weather.stack.access.key=YOUR_WEATHERSTACK_API_KEY
   open.weather.app.id=YOUR_OPENWEATHER_API_KEY
3. **Run the application**
4. **Access the service**  
   API endpoint: [`http://localhost:7777/v1/weather?city=YourCity`](http://localhost:7777/v1/weather?city=YourCity)







