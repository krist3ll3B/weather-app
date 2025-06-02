# weather-app

This simple weather app is built using **Spring Boot Java** and integrates with **WeatherStack** and **OpenWeather** to provide weather data. It requires API keys for both services to function properly.

## Prerequisites

Ensure you have the following installed before running the application:
- **Java 8** (Check with `java -version`)
- **Maven** (Check with `mvn -version`)
- **API keys** for WeatherStack and OpenWeather

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
3. **Execute test using Maven**  
   ```sh
   mvn test
4. **Start the Application**  
   ```sh
   mvn spring-boot:run
5. **Access the service**  
   API endpoint: [`http://localhost:7777/v1/weather?city=YourCity`](http://localhost:7777/v1/weather?city=YourCity)







