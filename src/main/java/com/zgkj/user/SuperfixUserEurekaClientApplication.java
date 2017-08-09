package com.zgkj.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SuperfixUserEurekaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperfixUserEurekaClientApplication.class, args);
	}
}
