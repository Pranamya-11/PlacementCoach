package com.pranamya.placementcoach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
		exclude = {DataSourceAutoConfiguration.class}
)
public class PlacementcoachApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlacementcoachApplication.class, args);
	}

}
