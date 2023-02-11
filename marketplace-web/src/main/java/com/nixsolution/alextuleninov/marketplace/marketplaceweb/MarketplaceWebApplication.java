/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main Marketplace Web class.
 * @version 01
 *
 * @author Oleksandr Tuleninov
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MarketplaceWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceWebApplication.class, args);
	}

}
