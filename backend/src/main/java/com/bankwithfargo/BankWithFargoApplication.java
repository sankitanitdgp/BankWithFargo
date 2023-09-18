package com.bankwithfargo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableAutoConfiguration
@SpringBootApplication
public class BankWithFargoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankWithFargoApplication.class, args);
	}

}
