package com.hystrixmicroservice.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
@EnableEurekaClient
@EnableCircuitBreaker
public class HystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixApplication.class, args);
	}

}
