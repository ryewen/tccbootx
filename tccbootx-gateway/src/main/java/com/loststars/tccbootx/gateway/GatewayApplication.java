package com.loststars.tccbootx.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run =  SpringApplication.run(GatewayApplication.class, args);
		//run.getBean(OrderGateway.class).sendMessage("4,5");
	}
}