package com.challenge.sample.kristelle_weather_app;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KristelleWeatherAppApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(KristelleWeatherAppApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}

}
