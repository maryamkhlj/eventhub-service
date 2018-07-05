package com.beacon.analyticscollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableFeignClients
public class BeaconAnalyticsCollectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeaconAnalyticsCollectionApplication.class, args);
	}
}
